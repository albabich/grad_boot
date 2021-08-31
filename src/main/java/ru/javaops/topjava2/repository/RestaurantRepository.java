package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT DISTINCT(r) FROM Restaurant r JOIN FETCH  r.menuItems m WHERE m.date=?1")
    List<Restaurant> getAllWithMenuItemsByDate(LocalDate date);

    @Query("SELECT DISTINCT(r) FROM Restaurant r JOIN FETCH  r.votes m WHERE m.date=:localDate")
    Collection<Restaurant> findAllByVoteDate(LocalDate localDate);
}
