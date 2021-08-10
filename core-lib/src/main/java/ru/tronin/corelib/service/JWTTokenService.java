package ru.tronin.corelib.service;

import com.sun.xml.bind.v2.TODO;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tronin.corelib.interfaces.ITokenService;
import ru.tronin.corelib.models.UserInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTTokenService implements ITokenService {

    @Value("$(jwt.secret)")
    String JWT_SECRET;

    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(15, ChronoUnit.MINUTES);
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
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);
        String email = jwsClaims.getBody()
                .getSubject();
        Long id = jwsClaims.getBody()
                .get("id", Long.class);
        List<String> roles = jwsClaims.getBody()
                .get("roles", List.class);


        return UserInfo.builder()
                .id(id)
                .email(email)
                .roles(roles)
                .build();
    }
}
