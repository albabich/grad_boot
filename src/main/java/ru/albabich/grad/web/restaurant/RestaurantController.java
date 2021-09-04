package ru.albabich.grad.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.to.RestaurantWithVotesTo;
import ru.albabich.grad.to.RestaurantWithMenuTo;
import ru.albabich.grad.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@CacheConfig(cacheNames = "restaurantsAndMenus")
@Slf4j
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable
    @GetMapping("/with-menu/today")
    public List<RestaurantWithMenuTo> getAllWithMenuItemsToday() {
        log.info("getAll with menuItems today");
        List<Restaurant> restaurants = restaurantRepository.getAllWithMenuItemsByDate(LocalDate.now());
        return RestaurantUtil.getTosWithMenu(restaurants);
    }

    @GetMapping("/with-votes/today")
    public List<RestaurantWithVotesTo> getAllWithVotesToday() {
        log.info("getAll with votes today");
        return RestaurantUtil.getTosWithVotes(restaurantRepository.findAllByVoteDate(LocalDate.now()));
    }
}
