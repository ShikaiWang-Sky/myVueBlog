package com.sky.myblog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sky
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtUtils {
    private String secret;
    private long expire;
    private String header;

    /**
     * generate jwt token
     *
     * @param userId user id
     * @return jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        // expire time
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ" , "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        // must write in try-catch block, since token is captured by exception
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error", e);
            return null;
        }
    }

    /**
     * check if token is expired
     * @param expiration expiration time in token
     * @return is earlier than now, return true, for expired
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
