package com.project.app.ws.security;

import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.CATEGORY_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.CATEGORY_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PRODUCT_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.PRODUCT_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.ORDER_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.INVOICES_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.PUT, SecurityConstants.INVOICES_ENDPOINT).permitAll()
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(), userRepository))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}
