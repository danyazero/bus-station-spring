package com.zero.springweb.model;

public enum HttpStatusCodes {
    UNAUTHORIZED(401),
    SESSION_EXPIRED(419),
    RETRY_WITH(449),
    SSL_HANDSHAKE_FAILED(525),
    NOT_ACCEPTABLE(406);

    private final int value;

    private HttpStatusCodes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
