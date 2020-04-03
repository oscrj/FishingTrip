package ecutb.fishingtrip.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/users/**").hasAuthority("ADMIN")
                //.antMatchers("/fishing/**").hasAnyAuthority("USER", "ADMIN")
                //.antMatchers("/index").permitAll()
                //.antMatchers("/register").permitAll()
                .antMatchers("/**").permitAll() //  Use during development...
                .and()
                .formLogin()
                .loginPage("/login")    //  Get method
                .loginProcessingUrl("/login")   //  Post method i haven't created. Spring security makes it for me.
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
    }
}
