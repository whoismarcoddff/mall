package com.example.backend.utils;

import com.example.backend.common.constant.SecurityConstants;
import com.example.backend.filter.JwtAuthorizationFilter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenUtils {
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);
    private static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    public static String createAccessToken(String username, String id, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenValue = Jwts.builder()
                .setHeaderParam("type", SecurityConstants.TOKEN_ACCESS_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                .setId(id)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        String token = SecurityConstants.TOKEN_PREFIX + tokenValue;
        return token;
    }

    private static Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        System.out.println("Claims: " + claims);
        return claims;
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

    public static String getId(String token) {
        Claims claims = getClaims(token);
        return claims.getId();
    }

    public boolean validateToken(String token) {
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
            System.out.println(e.getMessage());
        }
        //TODO: redis logged-out blacklist
        return false;
    }
}
