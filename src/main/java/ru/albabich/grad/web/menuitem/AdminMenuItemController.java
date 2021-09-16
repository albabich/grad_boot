package ru.albabich.grad.web.menuitem;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.albabich.grad.error.NotFoundException;
import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.repository.MenuItemRepository;
import ru.albabich.grad.repository.RestaurantRepository;
import ru.albabich.grad.to.MenuItemTo;
import ru.albabich.grad.util.MenuItemUtil;
import ru.albabich.grad.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.time.LocalDate.now;
import static ru.albabich.grad.util.MenuItemUtil.createTo;


@RestController
@CacheConfig(cacheNames = "restaurantsAndMenus")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu-items";

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public MenuItemTo get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menuItem {} for restaurant {}", id, restaurantId);
        return createTo(menuItemRepository.get(id, restaurantId).orElseThrow(() -> new NotFoundException(String.format("MenuItem %s for restaurant %s not found", id, restaurantId))));
    }

    @GetMapping()
    public List<MenuItemTo> getAllForRestaurant(@PathVariable int restaurantId) {
        log.info("get menuItems for restaurant {}", restaurantId);
        return MenuItemUtil.getTos(menuItemRepository.getAllForRestaurant(restaurantId));
    }

    @GetMapping("/today")
    public List<MenuItemTo> getAllForRestaurantToday(@PathVariable int restaurantId) {
        log.info("get menuItems for restaurant {} today", restaurantId);
        return MenuItemUtil.getTos(menuItemRepository.getAllForRestaurantByDate(restaurantId, now()));
    }

    @CacheEvict(allEntries = true, condition ="#menuItemTo.available.isEqual(T (java.time.LocalDate).now())")
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemTo> createWithLocation(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int restaurantId) {
        log.info("create menuItem{} for restaurant {}", menuItemTo, restaurantId);
        ValidationUtil.checkNew(menuItemTo);
        MenuItem menuItem = MenuItemUtil.createNewFromTo(menuItemTo);
        menuItem.setRestaurant(restaurantRepository.getById(restaurantId));
        MenuItemTo created = createTo(menuItemRepository.save(menuItem));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @CacheEvict(allEntries = true, condition ="#menuItemTo.available.isEqual(T (java.time.LocalDate).now())")
    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update menuItem {} for restaurant {}", id, restaurantId);
        ValidationUtil.assureIdConsistent(menuItemTo, id);
        MenuItem menuItem = menuItemRepository.checkBelong(id, restaurantId);
        MenuItemUtil.updateFromTo(menuItem, menuItemTo);
        menuItemRepository.save(menuItem);
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete menuItem {} for restaurant {}", id, restaurantId);
        MenuItem menuItem = menuItemRepository.checkBelong(id, restaurantId);
        menuItemRepository.delete(menuItem);
    }
}
