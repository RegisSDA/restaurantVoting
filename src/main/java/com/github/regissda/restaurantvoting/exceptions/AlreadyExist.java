package com.github.regissda.restaurantvoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by MSI on 23.09.2017.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExist extends RuntimeException {
    public AlreadyExist() {
        super("User already exist");
    }
}
