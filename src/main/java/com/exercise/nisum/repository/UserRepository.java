package com.exercise.nisum.repository;

import com.exercise.nisum.model.UserModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findById(String userId);
    Optional<UserModel> findByEmail(String userId);

    @Modifying
    @Query("UPDATE UserModel u SET u.lastLogin = :now, u.token = :token WHERE u.email = :email")
    void updateTokenByEmail(@Param("token") String token, @Param("email") String email, @Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM UserModel u WHERE u.id = :id")
    Integer deleteUserById(@Param("id") String id);

}
