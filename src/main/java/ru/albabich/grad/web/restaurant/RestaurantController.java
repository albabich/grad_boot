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
import ru.albabich.grad.to.RestaurantTo;
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
    public List<Restaurant> getAllWithMenuItemsToday() {
        log.info("getAll with menuItems today");
        return restaurantRepository.getAllWithMenuItemsByDate(LocalDate.now());
    }

    @GetMapping("/with-votes/today")
    public List<RestaurantTo> getAllWithVotesToday() {
        log.info("getAll with votes today");
        return RestaurantUtil.getTos(restaurantRepository.findAllByVoteDate(LocalDate.now()));
    }
}
