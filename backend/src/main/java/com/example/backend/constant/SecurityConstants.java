package com.example.backend.constant;

public final class SecurityConstants {
    public static final String ROLE_CLAIMS = "rol";
    public static final String ID_CLAIMS = "id";

    public static final long EXPIRATION = 30 * 60L;

    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    // Should be defined in Environment Variables
    public static final String JWT_SECRET_KEY = "jwt_secret_key";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "MARCO";
    public static final String TOKEN_ACCESS_TYPE = "ACCESS";
    public static final String TOKEN_REFRESH_TYPE = "REFRESH";

    public static final String LOGIN_WHITELIST = "/api/auth/login";

    public static final String FILTER_ALL = "/api/**";

    public SecurityConstants() {
    }
}
