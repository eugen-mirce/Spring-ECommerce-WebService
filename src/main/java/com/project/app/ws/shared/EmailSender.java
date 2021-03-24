package com.project.app.ws.shared;

import com.project.app.ws.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class EmailSender {
    @Autowired
    JavaMailSender javaMailSender;


    /* EMAIL TEMPLATE */
    final String FROM = "genimirce92@gmail.com";
    final String SUBJECT = "One Last Step To Complete Registration";
    final String PASSWORD_RESET_SUBJECT = "Password Reset Request";
    final String HTML_BODY =
            "<h1>Please verify your email address</h1>"
                    +   "<p>Thank you for registering with our app. To complete the registration process click on the following link: "
                    +   "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
                    +   "Final Step To Complete Registration</a><br/><br/>"
                    +   "Thank you and we are waiting for you!";
    final String PASSWORD_RESET_HTML_BODY =
            "<h1>A request to reset your password</h1>"
                    +   "<p>Hi $firstName!</p>"
                    +   "<p>Someone has requested to reset your password in our application. If it were not you, than ignore this email. "
                    +   "Otherwise please click on the following link to reset your password: "
                    +   "<a href='http://localhost:8080/verification-service/password-reset.html?token=$tokenValue'>"
                    +   "Click here to reset your password</a><br/><br/>"
                    +   "Thank you!";

    public void verifyEmail(UserDto userDto) throws UnsupportedEncodingException, MessagingException {

        String htmlBodyWithToken = HTML_BODY.replace("$tokenValue",userDto.getEmailVerificationToken());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(FROM,"AppName");
        helper.setTo(userDto.getEmail());
        helper.setSubject(SUBJECT);
        helper.setText(htmlBodyWithToken, true);

        javaMailSender.send(message);
    }
    public boolean sendPasswordResetRequest(String firstName, String email, String token)
            throws UnsupportedEncodingException, MessagingException {

        String  htmlBody = PASSWORD_RESET_HTML_BODY.replace("$firstName",firstName);
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
