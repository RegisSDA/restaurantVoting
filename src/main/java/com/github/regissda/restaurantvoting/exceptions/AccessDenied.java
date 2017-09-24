package com.github.regissda.restaurantvoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by MSI on 23.09.2017.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDenied extends RuntimeException {
    public AccessDenied() {
        super("Can't edit user with admin rights");
    }
}
