package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE  v.voteDate=:date AND v.user.id=:userId")
    Optional<Vote> getByDateAndUser(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v WHERE  v.id=:id AND v.user.id=:userId")
    Optional<Vote> getByIdAndUser(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE  v.user.id=:userId")
    List<Vote> getAllForUser(int userId);
}
