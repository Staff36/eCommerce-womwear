package ru.tronin.corelib.service;

import com.sun.xml.bind.v2.TODO;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.interfaces.IRedisTokenRepository;
import ru.tronin.corelib.interfaces.ITokenService;
import ru.tronin.corelib.models.UserInfo;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class JWTTokenService implements ITokenService {

    @Value("$(jwt.secret)")
    String JWT_SECRET;

    @Autowired
    IRedisTokenRepository repository;


    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(2, ChronoUnit.MINUTES);
        Date expirationDate = Date.from(expirationTime);
        String compactTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getEmail())
                .claim("roles", user.getRoles())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .setExpiration(expirationDate)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public UserInfo parseToken(String token) throws ExpiredJwtException {
        Jws<Claims> claims = getClaimsFromToken(token);
        String email = claims.getBody()
                .getSubject();
        Long id = claims.getBody()
                .get("id", Long.class);
        List<String> roles = claims.getBody().get("roles", List.class);
        return UserInfo.builder()
                .id(id)
                .email(email)
                .roles(roles)
                .build();
    }

    @Override
    public Date getExpirationDateFromToken(String token){
        Claims body = getClaimsFromToken(token).getBody();
        return body
                .getExpiration();
    }

    public boolean putTokenToRedis(String token) {
        String token1 = token.substring(6);
        Date now = new Date();
        Date expirationDate = getExpirationDateFromToken(token1);
        Long expirationTime = (expirationDate.getTime() - now.getTime()) / 1000;
        log.info("Expiration time contains " + expirationDate + " seconds.");
        boolean result = false;
        if (expirationTime > 5){
            result = repository.put(token, Duration.ofSeconds(expirationTime));
        }
        return result;
    }

    public String getTokenFromRedis(String token) {
        return repository.get(token);
    }

    public boolean isRedisHasToken(String token) {
        return repository.hasKey(token);
    }

    private Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);
    }
}
