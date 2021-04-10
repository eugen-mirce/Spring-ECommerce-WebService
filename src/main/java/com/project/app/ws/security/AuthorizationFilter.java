package com.project.app.ws.security;

import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }
    @Override
    public void doFilterInternal(HttpServletRequest req,
                                 HttpServletResponse res,
                                 FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req,res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req,res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if(token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getSecurityToken())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(user != null) {
                UserEntity userEntity = userRepository.findByEmail(user);
                if(userEntity == null) return null;

                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
