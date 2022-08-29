package com.exercise.nisum.repository;

import com.exercise.nisum.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findById(String userId);
}
