package com.platform.repository;

import com.platform.common.model.StatusEnum;
import com.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndStatus(Long userId, StatusEnum statusEnum);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatusAllIgnoreCase(String email, String status);

    Optional<User> findByMobileNumberAndStatusAllIgnoreCase(String mobileNumber, String status);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUsernameAndPassword(String username, String password);

}
