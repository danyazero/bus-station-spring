package com.zero.springweb.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.springweb.model.HttpStatusCodes;
import com.zero.springweb.plugins.AuthenticationModule;
import com.zero.springweb.utils.RememberMeAuthenticationTokenFactory;
import com.zero.springweb.utils.UserPrincipalAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationModule authenticationModule;
    static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        val extractedSessionToken = extractSessionTokenFromRequest(request);
        val requestURI = request.getRequestURI();
        if (extractedSessionToken.isEmpty() && !requestURI.matches("^/api/[a-z]+$")) {
            logger.info("Error Session token is empty");
            response.setStatus(HttpStatusCodes.UNAUTHORIZED.getValue());
            return;
        }
        Optional<DecodedJWT> decodedJWT;
        try {
            decodedJWT = authenticationModule.decodeSessionToken(extractedSessionToken);
        }catch (Exception e) {
            logger.info("Error while extraction SessionToken: " + e.getMessage());
            decodedJWT = Optional.empty();
        }

        if (isTokenPresentAndNotAuthorizationURI(extractedSessionToken, decodedJWT)) {
            val extractedRefreshToken = extractRefreshTokenFromRequest(request);
            try {
                decodedJWT = authenticationModule.tryResumeSessionWithRefreshToken(extractedRefreshToken, response);
            } catch (Exception e) {
                logger.info("Error while extraction RefreshToken");
                response.setStatus(HttpStatusCodes.UNAUTHORIZED.getValue());
                return;
            }
        }


        if (isTokenRefreshed(decodedJWT)) {
            logger.info("trying auth user rememberMe method");
            authenticationModule.authenticateWithSessionToken(decodedJWT, RememberMeAuthenticationTokenFactory.class);
        } else {
            logger.info("trying auth user standard method");
            authenticationModule.authenticateWithSessionToken(decodedJWT, UserPrincipalAuthenticationToken.class);
        }

        filterChain.doFilter(request, response);
    }

    private static Boolean isTokenRefreshed(Optional<DecodedJWT> decodedJWT) {
        if (decodedJWT.isEmpty()) return false;
        return decodedJWT.get().getClaim("rwt").asBoolean();
    }

    private static boolean isTokenPresentAndNotAuthorizationURI(Optional<String> extractedToken, Optional<DecodedJWT> decodedJWT) {
        return extractedToken.isPresent() && decodedJWT.isEmpty();
    }

    private Optional<String> extractSessionTokenFromRequest(HttpServletRequest request) {
        val cookie = WebUtils.getCookie(request, "AuthToken");
        if (cookie != null) {
            logger.info("Cookie: {}", cookie.getValue());
            return Optional.of(cookie.getValue());
        }

        return Optional.empty();
    }

    private Optional<String> extractRefreshTokenFromRequest(HttpServletRequest request) {
        val cookie = WebUtils.getCookie(request, "RefreshToken");
        if (cookie != null) {
            logger.info("Refresh Cookie: {}", cookie.getValue());
            return Optional.of(cookie.getValue());
        }

        return Optional.empty();
    }
}
