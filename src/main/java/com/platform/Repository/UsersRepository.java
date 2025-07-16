package com.platform.Repository;

import com.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatusAllIgnoreCase(String email, String status);

    Optional<User> findByMobileNumberAndStatusAllIgnoreCase(String mobileNumber, String status);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUsernameAndPassword(String username, String password);

}
