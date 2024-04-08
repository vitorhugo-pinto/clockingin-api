package com.vhp.clockinginapi.configurations.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticateFilter extends OncePerRequestFilter {

  private JWTService jwtService;

  AuthenticateFilter(JWTService jwtService){
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
      String headerToken = request.getHeader("Authorization");

      if(headerToken != null ){
        var jwtDecoded = this.jwtService.validateToken(headerToken);
        if(jwtDecoded == null){
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }
        request.setAttribute("userId", jwtDecoded.getSubject());
                    
        var rolesFromClaim = jwtDecoded.getClaim("roles").asList(Object.class);

        List<SimpleGrantedAuthority> authorities = rolesFromClaim.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

        System.out.println(authorities);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtDecoded.getSubject(), null, authorities);
                    
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    filterChain.doFilter(request, response);
  }
  
}
