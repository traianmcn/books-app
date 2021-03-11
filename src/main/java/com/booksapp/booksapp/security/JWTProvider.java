package com.booksapp.booksapp.security;

import io.jsonwebtoken.*;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.booksapp.booksapp.security.SecurityConstants.SECRET_KEY;

@Component
public class JWTProvider {

    public String generateJWT(Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        Date expirationDate = new Date(new Date().getTime() + SecurityConstants.TOKEN_EXPIRATION);

        return Jwts.builder()
                .setSubject(currentUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getJWTFromRequest(HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }

    public String getSubjectFromJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException exception) {
            System.out.println("ERROR" + "Invalid Jwt signature.");
        } catch (MalformedJwtException exception) {
            System.out.println("ERROR" + "Invalid Jwt token.");
        } catch (ExpiredJwtException exception) {
            System.out.println("ERROR" + "Expired Jwt token.");
        } catch (UnsupportedJwtException exception) {
            System.out.println("ERROR" + "Unsuported Jwt token.");
        } catch (IllegalArgumentException exception) {
            System.out.println("ERROR" + "Jwt claims is empty.");
        }
        return false;
    }
}
