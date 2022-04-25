package ru.tronin.corelib.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoEntityException extends RuntimeException {
    public NoEntityException(String message) {
        super(message);
    }
}
