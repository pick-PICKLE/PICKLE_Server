package com.pickle.server.user.repository;

import com.pickle.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email")String email);
    Optional<User> findUserByOrigin(@Param("origin") String origin);

}