package hieu.shopappudemyhoang.config;

import hieu.shopappudemyhoang.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JWT-TOKEN")
public class JwtTokenUtils {

    @Value("${app-jwt-expiration-milliseconds}")
    private int expirationMilliseconds;

    @Value("${app.jwt-secret}")
    private String secretKey;

    public String generateToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        log.info("Generate token for user phone = " + user.getPhoneNumber());
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());

        return Jwts.builder()
               .setClaims(claims)
               .setSubject(user.getPhoneNumber())
               .setExpiration(new Date(System.currentTimeMillis() + expirationMilliseconds))
               .signWith(getSignInKey(), SignatureAlgorithm.ES256)
               .compact();
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
               .setSigningKey(getSignInKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
    }
}
