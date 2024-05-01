package com.zero.springweb.plugins.jwtModule;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.springweb.config.JwtProperties;
import com.zero.springweb.model.Credentials;
import com.zero.springweb.plugins.jwtModule.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Setter
public class JWTModule {
    private final JwtProperties properties;
    static final Logger logger = LoggerFactory.getLogger(JWTModule.class);

    /**
     * Duration of session token (Seconds) by default 45 seconds
     */
    private int sessionDuration = 45;

    /**
     * Duration of refresh token (Days) by default 30 days
     */
    private int refreshDuration = 30;
    /**
     * Parameter that indicates that sessionToken generated from refreshToken
     */
    private boolean refreshedWithToken = false;

    /**
     * Issue session JWT token with email and roles fields.
     * Duration: 1 min
     * Algorithm HMAC256
     *
     * @param credentials {userId, phone, roles}
     * @return String
     */
    public String issueSession(Credentials credentials) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf(credentials.getUserId()))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(Duration.ofSeconds(sessionDuration)))// 900 - 15min
                .withClaim("rwt", refreshedWithToken)
                .sign(Algorithm.HMAC256(properties.getSessionTokenSecretKey()));
    }

    public String issueSession(Credentials credentials, boolean isRefreshed) {
        setRefreshedWithToken(isRefreshed);
        return issueSession(credentials);
    }
    public String issueSession(Credentials credentials, boolean isRefreshed, int duration) {
        setSessionDuration(duration);
        return issueSession(credentials, isRefreshed);
    }

    /**
     * Issue refresh JWT token with email field.
     * Duration: 30 days
     * Algorithm HMAC256
     *
     * @param credentials {userId, phone, roles}
     * @return String
     */
    public RefreshToken issueRefresh(Credentials credentials) {
        var now = Instant.now();
        var session = issueSession(credentials);
        var refresh = JWT.create()
                .withSubject(String.valueOf(credentials.getUserId()))
                .withIssuedAt(now)
                .withNotBefore(now.plus(Duration.ofSeconds(sessionDuration - 10)))
                .withExpiresAt(now.plus(Duration.ofDays(refreshDuration)))
                .sign(Algorithm.HMAC256(properties.getRefreshTokenSecretKey()));

        return new RefreshToken().setSessionToken(session).setRefreshToken(refresh);
    }

    /**
     * Decode token by algorithm HMAC256 and provided secretKey
     *
     * @param token JWT token
     * @param secretKey secret key
     * @return DecodedJWT
     */
    public DecodedJWT decode(String token, String secretKey) throws JWTVerificationException {
        logger.info("Secret key: {}", secretKey);
        logger.info("Provided token: {}", token);
        var decoded = JWT.require(Algorithm.HMAC256(secretKey)).build();
        return decoded.verify(token);
    }

}
