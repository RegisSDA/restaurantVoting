package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.repository.VotesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.regissda.restaurantvoting.web.UserRestController.REST_USER_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@RestController
@RequestMapping(value = REST_USER_URL+"/votes")
public class UserRestController {
    static final String REST_USER_URL = "rest/v1/user";

    @Autowired
    private VotesDAO votesDAO;


}
