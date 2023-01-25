package com.pickle.server.user.repository;

import com.pickle.server.user.domain.User;

import com.pickle.server.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(value="select * from user u where u.user_id= :user_id",nativeQuery=true)
   // Long findUserId(@Param("user_id") Long user_id);
//@Query(value="select * from user u where u.email= :email",nativeQuery=true)
//User findByEmail(@Param("email") String email);
   // Optional<User> findByEmail(String email);
 //   Optional<User> findById(Long user_id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}