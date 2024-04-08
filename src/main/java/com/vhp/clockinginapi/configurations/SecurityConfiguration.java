package com.vhp.clockinginapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
  
  private static final String[] PERMITED_LIST = { "**" };

  private final AuthenticationProvider authenticationProvider;

  public SecurityConfiguration(AuthenticationProvider authenticationProvider) {
      this.authenticationProvider = authenticationProvider;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(crsf -> crsf.disable())
      .authorizeHttpRequests(req -> req.requestMatchers(PERMITED_LIST)
              .permitAll()
              .anyRequest()
              .permitAll())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider);

    return http.build();
  }
}
