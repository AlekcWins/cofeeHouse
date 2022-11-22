package ru.cofee.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cofee.house.model.auth.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
