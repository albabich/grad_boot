package ru.javaops.topjava2.web.menuitem;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuItemRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.MenuItemTo;
import ru.javaops.topjava2.util.MenuItemUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.javaops.topjava2.util.MenuItemUtil.updateFromTo;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;


@RestController
@CacheConfig(cacheNames = "restaurantsAndMenus")
@Slf4j
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/restaurants";

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminMenuItemController(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{restaurantId}/menu-items/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menuItem {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(menuItemRepository.get(id, restaurantId));
    }

    @CacheEvict(allEntries = true)
    @Transactional
    @PostMapping(value = "/{restaurantId}/menu-items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int restaurantId) {
        log.info("create menuItem{} for restaurant {}", menuItemTo, restaurantId);
        checkNew(menuItemTo);
        MenuItem menuItem = MenuItemUtil.createNewFromTo(menuItemTo);
        menuItem.setRestaurant(restaurantRepository.getById(restaurantId));
        MenuItem created = menuItemRepository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/menu-items" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    @PutMapping(value = "/{restaurantId}/menu-items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update menuItem {} for restaurant {}", id, restaurantId);
        assureIdConsistent(menuItemTo, id);
        MenuItem menuItem = menuItemRepository.checkBelong(id, restaurantId);
        updateFromTo(menuItem, menuItemTo);
        menuItemRepository.save(menuItem);
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping("/{restaurantId}/menu-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete menuItem {} for restaurant {}", id, restaurantId);
        MenuItem menuItem = menuItemRepository.checkBelong(id, restaurantId);
        menuItemRepository.delete(menuItem);
    }
}
