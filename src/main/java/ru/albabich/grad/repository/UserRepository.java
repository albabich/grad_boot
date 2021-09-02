package ru.albabich.grad.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}