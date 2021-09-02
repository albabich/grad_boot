package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.Vote;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE  v.date=:date AND v.user.id=:userId")
    Vote getByDateAndUser(LocalDate date, int userId);
}
