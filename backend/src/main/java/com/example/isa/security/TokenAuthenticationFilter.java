package com.example.isa.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    protected final Log LOGGER = LogFactory.getLog(getClass());
    private final TokenHandler tokenHandler;
    private final UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenHandler tokenHandler, UserDetailsService userDetailsService) {
        this.tokenHandler = tokenHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String username;
        String authToken = tokenHandler.getToken(request);

        try {
            if (authToken != null) {
                username = tokenHandler.getUsernameFromToken(authToken);
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (tokenHandler.validateToken(authToken, userDetails)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setAccessToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (ExpiredJwtException ex) {
            //if token.subject == access
            LOGGER.debug("Token expired!");
            if(request.getServletPath().equals("/api/token/refresh")) {
                username = tokenHandler.getUsernameFromToken(authToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                String newAccessToken = tokenHandler.generateAccessToken(username);
                authentication.setAccessToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            //if token.subject=refresh: error!!!
        }
        chain.doFilter(request, response);
    }

}