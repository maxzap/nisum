package com.exercise.nisum.service;

import com.exercise.nisum.dto.user.CreateUserRequestDTO;
import com.exercise.nisum.dto.user.CreatedUserResponseDTO;
import com.exercise.nisum.model.UserModel;

import java.util.List;

public interface UserService {

    CreatedUserResponseDTO registerUser(CreateUserRequestDTO createUserRequestDTO);
    UserModel getUserByUserId(String userId);

    List<UserModel> findAllUsers();

    Integer deleteUserById(String email);
}
