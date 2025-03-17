package hieu.shopappudemyhoang.config;

import hieu.shopappudemyhoang.entity.Role;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        String dobStr = user.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE);

        log.info("Generate token for user phone = " + user.getPhoneNumber());
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("name", user.getFullName());
        claims.put("dob", dobStr);
        claims.put("role", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return Jwts.builder()
               .claims(claims)
               .subject(user.getPhoneNumber())
               .expiration(new Date(System.currentTimeMillis() + expirationMilliseconds))
               .signWith(getSignInKey(), SignatureAlgorithm.HS256)
               .compact();
    }



    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
               .setSigningKey(getSignInKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractClaims(token);
        return claimsResolver.apply(claims);
    }

    // check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}
