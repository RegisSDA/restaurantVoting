package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.AuthorizedUser;
import com.github.regissda.restaurantvoting.exceptions.VotingOverException;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.service.RestaurantsService;
import com.github.regissda.restaurantvoting.service.VotesService;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.RestaurantWithVotes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.github.regissda.restaurantvoting.web.UserRestController.REST_USER_URL;

/**
 * Created by MSI on 14.09.2017.
 */

@Controller
@RequestMapping(value = REST_USER_URL)
public class UserRestController {
    static final String REST_USER_URL = "rest/v1/user";
    public static final LocalTime VOTING_STOP_TIME = LocalTime.of(11, 0);

    @Autowired
    private VotesService votesService;
    @Autowired
    private RestaurantsService restaurantsService;


    @GetMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VoteTO>> getVotes() {
        return new ResponseEntity<>(votesService.getVotesOfUser(AuthorizedUser.getUserName()), HttpStatus.OK);
    }

    @GetMapping(value = "/votes/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTO> getVote(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(votesService.getVote(AuthorizedUser.getUserName(), date), HttpStatus.OK);
    }

    @PostMapping(value = "/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createVote(@RequestBody VoteTO voteTO) {
        URI uriOfNewResource;
        Vote saved;
        if (LocalTime.now().isBefore(VOTING_STOP_TIME) && voteTO.getDate().equals(LocalDate.now().toString())) {
            saved = votesService.create(voteTO, AuthorizedUser.getUserName());
            uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_USER_URL + "/votes/{id}")
                    .buildAndExpand(saved.getId()).toUri();
        } else {
            throw new VotingOverException();
        }
        return ResponseEntity.created(uriOfNewResource).body(null);
    }


    @PutMapping(value = "/votes/{dateTime}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateVote(@RequestBody VoteTO voteTO,
                                     @PathVariable("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        if (!date.toString().equals(voteTO.getDate())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (LocalTime.now().isBefore(VOTING_STOP_TIME) && voteTO.getDate().equals(LocalDate.now().toString())) {
            votesService.update(voteTO, AuthorizedUser.getUserName());
        } else {
            throw new VotingOverException();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/votes/{id}")
    public ResponseEntity deleteCurrentVote(@PathVariable("id") Integer id) {
        votesService.delete(id, AuthorizedUser.getUserName());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "restaurants/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantWithVotes>> getRestaurantsRating(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(votesService.getRestaurantsWithVotes(date), HttpStatus.OK);
    }


    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantWithDishes>> getRestaurants() {
        return new ResponseEntity<>(restaurantsService.getRestaurantsWithDishes(), HttpStatus.OK);
    }

    @Cacheable(value = "top1")
    @GetMapping(value = "restaurants/top1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithDishes> getTop(@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(restaurantsService.getTop(date), HttpStatus.OK);
    }

}
