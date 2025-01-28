package com.artemsolovev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user").hasAnyRole("ADMIN")
                .antMatchers("/user/search").hasAnyRole("ADMIN", "QUIZUSER")
                .antMatchers(HttpMethod.POST, "/quizuser").permitAll()
                .antMatchers(HttpMethod.GET, "/quizuser").hasAnyRole("ADMIN")
                .antMatchers("/admin").permitAll()
                .antMatchers("/quiz").hasAnyRole("QUIZUSER", "ADMIN")
                .antMatchers("/answer").hasAnyRole("QUIZUSER")
                .antMatchers("/answer/**").hasAnyRole("QUIZUSER", "ADMIN")


                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
