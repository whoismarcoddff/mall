package com.example.backend.security;

import com.example.backend.constant.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    public static String createAccessToken(String username, String id, List<String> roles, boolean isRememberMe) {
        System.out.println("id createAccess: " + id);
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenValue = Jwts.builder()
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET_KEY)
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                .claim(SecurityConstants.ID_CLAIMS, id)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        String token = SecurityConstants.TOKEN_PREFIX + tokenValue;
        return token;
    }

    private static Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return claims;
    }

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaims(token);
            username = claims.getSubject();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            username = null;
        }
        return username;
    }

    public static String getIdFromToken(String token) {
        String id;
        try {
            Claims claims = getClaims(token);
            return (String) claims.get(SecurityConstants.ID_CLAIMS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            id = null;
        }
        return id;
    }

    public static Date getExpiredDateFromToken(String token) {
        return getClaims(token).getExpiration();
    }

    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get(SecurityConstants.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT token compact of handler are invalid.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        //TODO: redis logged-out blacklist
        return false;
    }

    public static String getCurrentTokenByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentToken = authentication.getCredentials().toString();
        return currentToken;
    }


}
