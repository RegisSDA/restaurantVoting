package com.github.regissda.restaurantvoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.github.regissda.restaurantvoting.web.UserRestController.VOTING_STOP_TIME;

/**
 * Created by MSI on 15.09.2017.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotingOverException extends RuntimeException {
    public VotingOverException() {
        super("Voting is over at "+ VOTING_STOP_TIME);
    }
}
