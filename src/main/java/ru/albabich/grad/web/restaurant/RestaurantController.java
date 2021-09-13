package ru.albabich.grad.web.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.to.RestaurantWithMenuTo;
import ru.albabich.grad.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@CacheConfig(cacheNames = "restaurantsAndMenus")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    @GetMapping()
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @GetMapping("/{id}/with-menu")
    public RestaurantWithMenuTo getWithMenuItemsToday(@PathVariable int id) {
        log.info("get restaurant {} with menuItems today", id);
        return RestaurantUtil.getToWithMenu(restaurantRepository.getWithMenuItemsByDate(id, LocalDate.now()));
    }

    @Cacheable
    @GetMapping("/with-menu")
    public List<RestaurantWithMenuTo> getAllWithMenuItemsToday() {
        log.info("getAll with menuItems today");
        return RestaurantUtil.getTosWithMenu(restaurantRepository.getAllWithMenuItemsByDate(LocalDate.now()));
    }
}
