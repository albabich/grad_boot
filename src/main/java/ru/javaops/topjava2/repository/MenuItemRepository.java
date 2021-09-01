package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.MenuItem;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT m FROM MenuItem m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<MenuItem> get(int id, int restaurantId);

    default MenuItem checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("MenuItem id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}