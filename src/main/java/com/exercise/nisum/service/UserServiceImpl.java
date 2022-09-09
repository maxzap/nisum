package com.exercise.nisum.service;

import com.exercise.nisum.dto.user.CreateUserRequestDTO;
import com.exercise.nisum.dto.user.CreatedUserResponseDTO;
import com.exercise.nisum.exception.NoSuchElementFoundException;
import com.exercise.nisum.model.PhoneModel;
import com.exercise.nisum.model.UserModel;
import com.exercise.nisum.repository.PhoneRepository;
import com.exercise.nisum.repository.UserRepository;
import com.exercise.nisum.util.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    JwtToken jwtToken;

    @Override
    public CreatedUserResponseDTO registerUser(CreateUserRequestDTO createUserRequestDTO) {

        var response = new CreatedUserResponseDTO();
        UUID uuid= UUID.randomUUID();
        var token = jwtToken.generateToken(createUserRequestDTO.getEmail());

        UserModel registeredUser = saveUserData(createUserRequestDTO, uuid, token);

        response.setToken(token);
        response.setIsActive(true);
        response.setLast_login(new Date(System.currentTimeMillis()));
        response.setCreated(new Date(System.currentTimeMillis()));
        response.setId(uuid.toString());

        return response;
    }

    @Override
    public UserModel getUserByUserId(String userId) throws RuntimeException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementFoundException(userId));
    }

    public List<UserModel> findAllUsers(){
        List<UserModel> result = new ArrayList<>();
        var users = userRepository.findAll();
        users.forEach(result::add);

        return result;
    }

    @Override
    public Integer deleteUserById(String id) {
        var user = userRepository.deleteUserById(id);
        return user;
    }

    private UserModel saveUserData(CreateUserRequestDTO createUserRequestDTO, UUID uuid, String token) {
        var user = new UserModel();
        user.setEmail(createUserRequestDTO.getEmail());
        user.setName(createUserRequestDTO.getName());
        user.setPassword(createUserRequestDTO.getPassword());
        user.setCreationDate(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(token);
        user.setId(uuid.toString());

        var registeredUser = userRepository.save(user);
        saveContactData(createUserRequestDTO, uuid.toString());
        return registeredUser;
    }

    private void saveContactData(CreateUserRequestDTO createUserRequestDTO, String id) {
        CompletableFuture.runAsync(() -> {
            List<PhoneModel> phones = createUserRequestDTO.getPhones().stream()
                    .map(phoneData -> {
                        PhoneModel phone = new PhoneModel();
                        phone.setUserId(id);
                        phone.setNumber(phoneData.getNumber());
                        phone.setCityCode(phoneData.getCityCode());
                        phone.setCountryCode(phoneData.getCountryCode());
                        return phone;
                    }).collect(Collectors.toList());
            phoneRepository.saveAll(phones);
        });
    }
}
