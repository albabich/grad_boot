package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT DISTINCT(r) FROM Restaurant r JOIN FETCH  r.menuItems m WHERE m.available=:date ORDER BY r.name")
    List<Restaurant> getAllWithMenuItemsByDate(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN FETCH  r.menuItems m WHERE r.id=:id AND m.available=:date")
    Restaurant getWithMenuItemsByDate(int id, LocalDate date);
}
