package ru.albabich.grad.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.error.IllegalRequestDataException;
import ru.albabich.grad.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE  v.voteDate=:date AND v.user.id=:userId")
    Optional<Vote> getByDateAndUser(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v WHERE  v.id=:id AND v.user.id=:userId AND v.voteDate=:date")
    Optional<Vote> getByIdAndUserAndDate(int id, int userId, LocalDate date);

    default Vote checkBelongAndDate(int id, int userId, LocalDate voteDate) {
        return getByIdAndUserAndDate(id, userId, voteDate).orElseThrow(() -> new IllegalRequestDataException("You can operate only your today's vote"));
    }
}
