package ru.tronin.msauth.models.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequestDto {

    String email;
    String password;
    List<String> roles;
}
