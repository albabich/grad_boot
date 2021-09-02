package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.Restaurant;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT DISTINCT(r) FROM Restaurant r JOIN FETCH  r.menuItems m WHERE m.date=:date ORDER BY r.name")
    List<Restaurant> getAllWithMenuItemsByDate(LocalDate date);

    @Query("SELECT DISTINCT(r) FROM Restaurant r JOIN FETCH  r.votes m WHERE m.date=:localDate ORDER BY r.name")
    Collection<Restaurant> findAllByVoteDate(LocalDate localDate);
}
