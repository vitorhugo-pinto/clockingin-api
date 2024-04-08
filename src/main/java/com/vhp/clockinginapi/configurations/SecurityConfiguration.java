package com.vhp.clockinginapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vhp.clockinginapi.configurations.jwt.AuthenticateFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  
  private static final String[] PERMITED_LIST = { "/authenticate" };

  private final AuthenticationProvider authenticationProvider;
  private AuthenticateFilter authenticateFilter;

  public SecurityConfiguration(AuthenticationProvider authenticationProvider, AuthenticateFilter authenticateFilter) {
      this.authenticationProvider = authenticationProvider;
      this.authenticateFilter = authenticateFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(crsf -> crsf.disable())
      .authorizeHttpRequests(req -> req.requestMatchers(PERMITED_LIST).permitAll()
              .anyRequest().authenticated())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
