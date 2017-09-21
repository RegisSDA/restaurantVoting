package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.AutorizedUser;
import com.github.regissda.restaurantvoting.exceptions.NotFoundException;
import com.github.regissda.restaurantvoting.exceptions.VotingOverException;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.repository.UsersDAO;
import com.github.regissda.restaurantvoting.repository.VotesDAO;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.RestaurantWithVotes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;
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
    private VotesDAO votesDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private RestaurantDAO restaurantDAO;


    @GetMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VoteTO>> getVotes() {
        return new ResponseEntity<>(votesDAO.findAllByUserEquals(usersDAO.getOne(AutorizedUser.getUserName()))
                .stream()
                .map(a -> convert(a, VoteTO.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/votes/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTO> getVote(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Vote vote = votesDAO.getByVoteDateAndUser(date, usersDAO.getOne(AutorizedUser.getUserName()));
        if (vote==null){
            throw new NotFoundException();
        }
        return new ResponseEntity<VoteTO>(convert(vote, VoteTO.class),HttpStatus.OK);
    }

    @CacheEvict(value = "top1", allEntries = true)
    @PostMapping(value = "/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity createOrUpdateVote(@RequestBody VoteTO voteTO) {
        URI uriOfNewResource;
        Vote saved;
        if (LocalTime.now().isBefore(VOTING_STOP_TIME) && voteTO.getDate().equals(LocalDate.now().toString())) {
            Vote vote = new Vote(voteTO.getId(),usersDAO.getOne(AutorizedUser.getUserName()), restaurantDAO.getOne(voteTO.getRestaurant()), LocalDate.now());
            saved = votesDAO.save(vote);
            uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_USER_URL + "/votes/{id}")
                    .buildAndExpand(saved.getId()).toUri();


        } else {
            throw new VotingOverException();
        }
        return ResponseEntity.created(uriOfNewResource).body(null);
    }

    @CacheEvict(value = "top1",allEntries = true)
    @DeleteMapping(value = "/votes/{id}")
    public ResponseEntity deleteCurrentVote(@PathVariable("id") Integer id) {
        if (LocalTime.now().isBefore(VOTING_STOP_TIME)) {
            votesDAO.delete(id);
        } else {
            throw new VotingOverException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "restaurants/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantWithVotes>> getRestaurantsRating(@RequestParam (value = "date",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if(date==null){
            date = LocalDate.now();
        }
        return new ResponseEntity<>(votesDAO.findAllByVoteDateEquals(date).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .map(a->new RestaurantWithVotes(a.getKey().getName(),a.getValue()))
                .sorted(Comparator.comparingLong(RestaurantWithVotes::getVotes))
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @Cacheable("restaurants")
    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantWithDishes>> getRestaurants(){
        System.out.println("Not Cache");
        return new ResponseEntity<>(restaurantDAO.findAllEager().stream().map(a->convert(a,RestaurantWithDishes.class)).collect(Collectors.toList()),HttpStatus.OK);
    }

    @Cacheable(value="top1")
    @GetMapping(value = "restaurants/top1",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithDishes> getTop(@RequestParam(name = "date",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if(date==null){
            date = LocalDate.now();
        }
        System.out.println("Not Cache");
        Optional<Map.Entry<Restaurant,Long>> top = votesDAO.findAllByVoteDateEquals(date).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator
                        .comparingLong((Map.Entry<Restaurant,Long> a)->a.getValue())
                        .thenComparing((Map.Entry<Restaurant,Long> a)->a.getKey().getName()));

        RestaurantWithDishes restaurant = null;
        if (top.isPresent()){
            restaurant = convert(restaurantDAO.findOneEager(top.get().getKey().getName()),RestaurantWithDishes.class);
        }
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }

}
