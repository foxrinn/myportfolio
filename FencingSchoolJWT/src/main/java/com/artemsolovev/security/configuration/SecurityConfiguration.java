package com.artemsolovev.security.configuration;

import com.artemsolovev.dto.ResponseResult;
import com.artemsolovev.security.jwt.JwtConfigurer;
import com.artemsolovev.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers("/user").authenticated()
                .antMatchers(HttpMethod.GET, "/user/**").authenticated()
                .antMatchers(HttpMethod.POST, "/apprentice").permitAll()
                .antMatchers(HttpMethod.GET, "/apprentice/**").hasAnyRole("ADMIN", "TRAINER", "APPRENTICE")
                .antMatchers("/apprentice/**").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers("/admin/**").permitAll()
                .antMatchers(HttpMethod.POST, "/admin").permitAll()
                .antMatchers(HttpMethod.POST,"/trainer/").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/trainer/").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers("/trainer/**").hasAnyRole("ADMIN", "TRAINER")
                .antMatchers("/trainerschedule/**").hasAnyRole("ADMIN", "TRAINER", "APPRENTICE")
                .antMatchers("/training/**").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers("/training/apprentice/**").hasAnyRole("ADMIN", "APPRENTICE")
                .antMatchers("/training/date").hasAnyRole("ADMIN", "APPRENTICE")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    request.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("utf-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    objectMapper.writeValue(response.getWriter(),
                            new ResponseResult<>("No authentication", null));
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    request.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("utf-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    objectMapper.writeValue(response.getWriter(),
                            new ResponseResult<>("Forbidden", null));
                })


                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //.formLogin();

    }
}
