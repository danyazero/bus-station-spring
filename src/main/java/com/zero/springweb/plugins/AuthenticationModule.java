package com.zero.springweb.plugins;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.springweb.config.JwtProperties;
import com.zero.springweb.model.AuthorizationException;
import com.zero.springweb.model.Credentials;
import com.zero.springweb.model.LoginResponse;
import com.zero.springweb.model.UserInfo;
import com.zero.springweb.plugins.jwtModule.JWTModule;
import com.zero.springweb.repository.UserRepository;
import com.zero.springweb.utils.JwtToPrincipalConverter;
import com.zero.springweb.utils.UserPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationModule {
    private final JWTModule jwtService;
    private final UserRepository repository;
    private final JwtProperties jwtProperties;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    static final Logger logger = LoggerFactory.getLogger(AuthenticationModule.class);

    public Optional<DecodedJWT> tryResumeSessionWithRefreshToken(Optional<String> extractedRefreshToken, HttpServletResponse response) throws Exception {
        logger.info("Extracting refreshToken");

        Optional<DecodedJWT> decodedJWT;
        val decodedRefreshJWT = decodeRefreshToken(extractedRefreshToken);
        decodedJWT = tryResumeSession(response, decodedRefreshJWT);

        return decodedJWT;
    }

    public <T extends AbstractAuthenticationToken>void authenticateWithSessionToken(Optional<DecodedJWT> decodedJWT, Class<T> authorizationMethod) {
        decodedJWT.map(jwtToPrincipalConverter::convert).map(userPrincipal -> getAuthenticationContext(userPrincipal, authorizationMethod))
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
    }

    public DecodedJWT decodeRefreshToken(Optional<String> extractedRefreshToken) {
        return jwtService.decode(extractedRefreshToken.orElseThrow(), jwtProperties.getRefreshTokenSecretKey());
    }

    public DecodedJWT decodeSessionToken(LoginResponse token) {
        return jwtService.decode(token.getToken(), jwtProperties.getSessionTokenSecretKey());
    }
    public Optional<DecodedJWT> decodeSessionToken(Optional<String> token) {
        return token.map(s -> jwtService.decode(s, jwtProperties.getSessionTokenSecretKey()));
    }

    private static <T extends AbstractAuthenticationToken> T getAuthenticationContext(UserPrincipal userPrincipal, Class<T> authMethodClass) {
        try {
            Constructor<T> constructor = authMethodClass.getConstructor(UserPrincipal.class);
            return constructor.newInstance(userPrincipal);
        } catch (Exception e) {
            return null;
        }
    }


    private Optional<DecodedJWT> tryResumeSession(HttpServletResponse response, DecodedJWT decodedRefreshJWT) throws Exception {
        LoginResponse token = attemptAuthorize(decodedRefreshJWT);
        Cookie authCookie = getSessionTokenCookie(token);
        logger.info("Created new session Cookie");

        response.addCookie(authCookie);
        return Optional.of(decodeSessionToken(token));
    }

    private Cookie getSessionTokenCookie(LoginResponse token) {
        Cookie authCookie = new Cookie("AuthToken", token.getToken());
        authCookie.setPath("/api");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(60 * 60 * 24 * 20);
        return authCookie;
    }

    private LoginResponse attemptAuthorize(DecodedJWT decodedToken) throws Exception {
        Integer userIdFromDecodedJwt = Integer.valueOf(decodedToken.getSubject());
        UserInfo user = getUserPrincipalIfPasswordNotChanged(userIdFromDecodedJwt, decodedToken.getIssuedAtAsInstant());

        var credentials = getRefreshedSessionToken(user);
        val sessionToken = jwtService.issueSession(credentials, true);

        return new LoginResponse().setToken(sessionToken);
    }


    private UserInfo getUserPrincipalIfPasswordNotChanged(Integer userId, Instant tokenIssuedTime) throws Exception {
        Optional<UserInfo> user = repository.findUserByIdEquals(userId);

        if (user.isPresent()) {
            val isPasswordChangeSinceIssuedToken = tokenIssuedTime.isBefore(user.get().getPasswordChanged());
            logger.info("RefreshToken check: {} {} {}", tokenIssuedTime, user.get().getPasswordChanged(), isPasswordChangeSinceIssuedToken);

            if (isPasswordChangeSinceIssuedToken)
                throw new AuthorizationException("The passwordCrypto has been changed. Unable to resume session");
        }

        return user.orElseThrow();
    }

    private static Credentials getRefreshedSessionToken(UserInfo user) {
        return Credentials.builder()
                .userId(user.getId())
                .phone(user.getPhone())
                .roles(Arrays.stream(user.getRole().split(", ")).toList())
                .build();
    }

}
