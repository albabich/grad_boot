package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.error.IllegalRequestDataException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT m FROM MenuItem m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<MenuItem> get(int id, int restaurantId);

    default MenuItem checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("MenuItem id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id=:restaurantId and m.date=:date ORDER BY m.id")
    List<MenuItem> getAllForRestaurantByDate(int restaurantId, LocalDate date);
}