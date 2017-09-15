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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;
import static com.github.regissda.restaurantvoting.web.UserRestController.REST_USER_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@RestController
@RequestMapping(value = REST_USER_URL)
public class UserRestController {
    static final String REST_USER_URL = "rest/v1/user";
    private final LocalTime VOTING_STOP_TIME = LocalTime.of(11, 0);

    @Autowired
    private VotesDAO votesDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private RestaurantDAO restaurantDAO;


    @GetMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTO> getVotes() {
        return votesDAO.findAllByUserEquals(usersDAO.getOne(AutorizedUser.getUserName()))
                .stream()
                .map(a -> convert(a, VoteTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/votes/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTO getVote(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Vote vote = votesDAO.getByVoteDateAndUser(date, usersDAO.getOne(AutorizedUser.getUserName()));
        if (vote==null){
            throw new NotFoundException();
        }
        return convert(vote, VoteTO.class);
    }

    @PostMapping(value = "/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrUpdateVote(@RequestBody VoteTO voteTO) {
        if (LocalTime.now().isBefore(VOTING_STOP_TIME) || !voteTO.getDate().equals(LocalDate.now().toString())) {
            Vote vote = new Vote(usersDAO.getOne(AutorizedUser.getUserName()), restaurantDAO.getOne(voteTO.getRestaurant()), LocalDate.now());
            votesDAO.save(vote);
        } else {
            throw new VotingOverException();
        }
    }

    @DeleteMapping(value = "/votes")
    public void deleteCurrentVote() {
        if (LocalTime.now().isBefore(VOTING_STOP_TIME)) {
            votesDAO.delete(new Vote(usersDAO.getOne(AutorizedUser.getUserName()), null, LocalDate.now()));
        } else {
            throw new VotingOverException();
        }
    }

    @GetMapping(value = "restaurants/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getRestaurantsRating(){
        return votesDAO.findAllByVoteDateEquals(LocalDate.now()).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .map(a->new RestaurantWithVotes(a.getKey().getName(),a.getValue()))
                .sorted(Comparator.comparingLong(RestaurantWithVotes::getVotes))
                .collect(Collectors.toList());

    }

    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithDishes> getRestaurants(){
        return restaurantDAO.findAll().stream().map(a->convert(a,RestaurantWithDishes.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "restaurants/top1",produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantWithDishes getTop(){
        Restaurant top = votesDAO.findAllByVoteDateEquals(LocalDate.now()).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(a->a.getValue())).get().getKey();

        return convert(top,RestaurantWithDishes.class);
    }

}
