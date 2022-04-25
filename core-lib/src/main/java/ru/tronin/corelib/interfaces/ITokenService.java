package ru.tronin.corelib.interfaces;

import ru.tronin.corelib.models.UserInfo;

import java.util.Date;

public interface ITokenService {

    String generateToken(UserInfo user);

    Date getExpirationDateFromToken(String token);

    UserInfo parseToken(String token);

    boolean putTokenToRedis(String token);

    String getTokenFromRedis(String token);

    boolean isRedisHasToken(String token);

    ;
}


