package com.tomwhwong.demorestapienvt.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // don't create HTTP session and not store logged in user's SecurityContext in the session
    /*
    The background of CSRF protection is to ensure that the user is not tricked into doing some unwanted action.
    As such, CSRF mostly acts as a protection against browser + session based attacks.
    When you use CSRF protection? Our recommendation is to use CSRF protection for any request that could be processed by a browser by normal users.
    If you are only creating a service that is used by non-browser clients, you will likely want to disable CSRF protection.
     */
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //.antMatchers("/**").permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .httpBasic()
            .realmName("Demo Rest API Person")
            .and()
            .csrf().disable();


    // --------------------------------
    // The following is only needed if you want to view H2-console for the development purpose


    // Note! only for development purpose! Enable access to H2 console
    httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
            .authorizeRequests().antMatchers("/h2-console").permitAll();
    // Note! only for development purpose!
    // disable CRSF (Cross Site Request Forgery)
    // httpSecurity.csrf().disable();

    // Note! only for development purpose!
    // add this line to use H2 web console, after Spring security has been enabled
    // this disable X-Frame Options in Spring Security
    httpSecurity.headers().frameOptions().disable();
  }

}
