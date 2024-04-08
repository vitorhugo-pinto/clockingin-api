package com.vhp.clockinginapi.configurations.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vhp.clockinginapi.dtos.AuthenticationResponseDTO;
import com.vhp.clockinginapi.models.UserEntity;

@Service
public class JWTService {

  @Value("${secrete.key}")
  private String SECRET;
  
  public DecodedJWT validateToken(String token) {
    final Algorithm TOKEN_SIGNATURE = Algorithm.HMAC256(SECRET);
    token = token.replace("Bearer ", "");
    try {
      return JWT.require(TOKEN_SIGNATURE).build().verify(token);
    } catch (JWTVerificationException e) {
      e.getStackTrace();
      return null;
    }
  }

  public AuthenticationResponseDTO generateToken(UserDetails userDetails, UserEntity user){
    final Instant TOKEN_EXPIRATION = Instant.now().plus(Duration.ofHours(2));
    final Algorithm TOKEN_SIGNATURE = Algorithm.HMAC256(SECRET);

    var jwt = JWT.create()
      .withIssuer(userDetails.getUsername())
      .withClaim("jobType", user.getJobType().toString())
      .withClaim("roles", Arrays.asList(
        userDetails.getAuthorities().stream().map(
          GrantedAuthority::getAuthority).collect(Collectors.joining(",")
        )
      ))
      .withSubject(user.getId().toString())
      .withExpiresAt(TOKEN_EXPIRATION)
      .sign(TOKEN_SIGNATURE);

      AuthenticationResponseDTO response = new AuthenticationResponseDTO(jwt);

    return response;
  }
}