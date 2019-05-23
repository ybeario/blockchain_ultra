package com.cqupt.bear.blockchain.idp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月27日 下午3:55:21 类说明
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("idpAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    @Qualifier("idpAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/assets/**", "/css/**", "/js/**", "/index", "/register", "/logout",
                        "/preview", "/pdfJS/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/researcher/**").hasRole("OFFICER")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "OFFICER", "USER").anyRequest().authenticated().and()
                .headers().frameOptions().disable().and().formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failureHandler)
                .loginProcessingUrl("/authentication/form").permitAll().and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll().and().csrf().disable();

    }
}
