package com.project.app.ws.service.Impl;

import com.project.app.shared.Utils;
import com.project.app.shared.dto.AddressDTO;
import com.project.app.shared.dto.UserDto;
import com.project.app.ui.model.response.ErrorMessages;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.PasswordResetTokenEntity;
import com.project.app.ws.io.repositories.PasswordResetTokenRepository;
import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JavaMailSender javaMailSender;


    /* EMAIL TEMPLATE */
    final String FROM = "genimirce92@gmail.com";
    final String SUBJECT = "One Last Step To Complete Registration";
    final String PASSWORD_RESET_SUBJECT = "Password Reset Request";
    final String HTMLBODY =
            "<h1>Please verify your email address</h1>"
        +   "<p>Thank you for registering with our app. To complete the registration process click on the following link: "
        +   "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
        +   "Final Step To Complete Registration</a><br/><br/>"
        +   "Thank you and we are waiting for you!";
    final String PASSWORD_RESET_HTMLBODY =
            "<h1>A request to reset your password</h1>"
        +   "<p>Hi $firstName!</p>"
        +   "<p>Someone has requested to reset your password in our application. If it were not you, than ignore this email. "
        +   "Otherwise please click on the following link to reset your password: "
        +   "<a href='http://localhost:8080/verification-service/password-reset.html?token=$tokenValue'>"
        +   "Click here to reset your password</a><br/><br/>"
        +   "Thank you!";
    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        for(int i=0; i < user.getAddresses().size(); i++) {
            AddressDTO address = user.getAddresses().get(i);
            address.setUserDetails(user);
            address.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(i,address);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));
        userEntity.setEmailVerificationStatus(false);
        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(storedUserDetails,UserDto.class);
        try{
            verifyEmail(returnValue);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);

        ModelMapper modelMapper = new ModelMapper();
        UserDto returnValue = modelMapper.map(userEntity,UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userEntity,UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId,UserDto user) {
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(updatedUserDetails,UserDto.class);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {

        if(page > 0) page++;

        ModelMapper modelMapper = new ModelMapper();

        List<UserDto> returnValue = new ArrayList<>();
        PageRequest pageableRequest = PageRequest.of(page,limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();
        for(UserEntity userEntity: users) {
            UserDto userDto = modelMapper.map(userEntity,UserDto.class);

            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        //Find user by token
        UserEntity userEntity = userRepository.findByEmailVerificationToken(token);

        if(userEntity != null) {
            boolean hasTokenExpired = Utils.hasTokenExpired(token);
            if(!hasTokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean requestPasswordReset(String email) {

        boolean returnValue = false;

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) {
            return false;
        }

        String token = utils.generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetEntity = new PasswordResetTokenEntity();
        passwordResetEntity.setToken(token);
        passwordResetEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetEntity);

        try {
            return sendPasswordResetRequest(
                    userEntity.getFirstName(),
                    userEntity.getEmail(),
                    token
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;

        // Check if token has expired
        if(Utils.hasTokenExpired(token)) {
            return returnValue;
        }

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);
        if(passwordResetTokenEntity == null) {
            return returnValue;
        }

        // Create the new encrypted password
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // Save the new user details
        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
        userEntity.setEncryptedPassword(encodedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // Check if password was changed successfully
        if(savedUserEntity != null && savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnValue = true;
        }

        //Remove reset token from database
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(),
                true,
                true,
                true,
                new ArrayList<>()
                );
    }
    public void verifyEmail(UserDto userDto) throws UnsupportedEncodingException, MessagingException {

        String htmlBodyWithToken = HTMLBODY.replace("$tokenValue",userDto.getEmailVerificationToken());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(FROM,"AppName");
        helper.setTo(userDto.getEmail());
        helper.setSubject(SUBJECT);
        helper.setText(htmlBodyWithToken, true);

        javaMailSender.send(message);
    }
    private boolean sendPasswordResetRequest(String firstName, String email, String token)
            throws UnsupportedEncodingException, MessagingException {

        String  htmlBody = PASSWORD_RESET_HTMLBODY.replace("$firstName",firstName);
                htmlBody = htmlBody.replace("$tokenValue",token);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(FROM,"AppName");
        helper.setTo(email);
        helper.setSubject(PASSWORD_RESET_SUBJECT);
        helper.setText(htmlBody, true);

        javaMailSender.send(message);
        return true;
    }
}
