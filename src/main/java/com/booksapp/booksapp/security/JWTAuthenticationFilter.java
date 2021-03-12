package com.booksapp.booksapp.security;

import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private AppUserDetailService appUserDetailService;
    @Autowired
    private JWTRedisService jwtRedisService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        try {
            String jwt = this.jwtProvider.getJWTFromRequest(httpServletRequest);
            System.out.println(jwt);
            System.out.println("=======>>>>>>>>>>You are here!");

            if (StringUtils.hasText(jwt) && this.jwtProvider.validateToken(jwt) && !jwtRedisService.isJWTBlackListed(jwt)) {
                String email = this.jwtProvider.getSubjectFromJWT(jwt);
                UserDetails userDetails = appUserDetailService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Error in JWTAuthenticationFilter" + e.getMessage());
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
