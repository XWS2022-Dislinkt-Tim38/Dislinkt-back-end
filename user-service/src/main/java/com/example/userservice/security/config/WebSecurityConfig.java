package com.example.userservice.security.config;

import com.example.userservice.security.auth.RestAuthenticationEntryPoint;
import com.example.userservice.security.auth.TokenAuthenticationFilter;
import com.example.userservice.security.util.TokenUtils;
import com.example.userservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                .authorizeRequests()
                .antMatchers("/user/getByTokenId/token").permitAll()
                .antMatchers("/user/role/testadmin").hasAuthority("ADMIN")
                .antMatchers("/user/role/testuser").hasAnyAuthority("USER")

                .anyRequest().authenticated()
                .and()
                .cors()
                .and()

                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), BasicAuthenticationFilter.class);
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/user/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/user/key/{key}");
        web.ignoring().antMatchers(HttpMethod.GET, "/user");
        web.ignoring().antMatchers(HttpMethod.PUT, "/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/user/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/user/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/token/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }

}