package ru.tronin.corelib.interfaces;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import ru.tronin.corelib.models.UserInfo;

import java.time.Duration;
import java.util.Date;

public interface ITokenService {

    String generateToken(UserInfo user);
    Date getExpirationDateFromToken(String token);
    UserInfo parseToken(String token);

    boolean putTokenToRedis(String token);
    String getTokenFromRedis(String token);
    boolean isRedisHasToken(String token) ;
;
}


