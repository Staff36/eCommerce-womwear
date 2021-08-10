package ru.tronin.corelib.interfaces;

import ru.tronin.corelib.models.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}


