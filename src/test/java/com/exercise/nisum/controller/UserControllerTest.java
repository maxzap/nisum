package com.exercise.nisum.controller;

import com.exercise.nisum.controller.UserController;
import com.exercise.nisum.dto.AuthenticateRequest;
import com.exercise.nisum.dto.AuthenticateResponse;
import com.exercise.nisum.dto.contact.ContactDataDTO;
import com.exercise.nisum.dto.user.CreateUserRequestDTO;
import com.exercise.nisum.dto.user.CreatedUserResponseDTO;
import com.exercise.nisum.model.UserModel;
import com.exercise.nisum.repository.UserRepository;
import com.exercise.nisum.service.MyUserDetailsService;
import com.exercise.nisum.service.UserService;
import com.exercise.nisum.util.jwt.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    public static final String TOKEN = "f4JvRkZj0Ea0hgqcvfDl.TaQGmdWLnj2yn5ngaXYNkGAmhfROxsNlbP2f3iTLuAaG4mgUZTIOabaDG4apgEdzsmq6qoc2vuLYgnDAslL.JcryrzJ9MNFGnpm0nkkvKzDpSvZ-nyzLDQ9XMVGCM1L";

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtToken jwtUtilService;

    @MockBean
    MyUserDetailsService myUserDetailsService;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Autowired
    private MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createUserTest__OK() throws Exception {

        CreateUserRequestDTO userData = new CreateUserRequestDTO("Juan Rodriguez", "juan@rodriguez.org", "hunter2!" ,
                List.of(new ContactDataDTO("1234567", "1", "57")));

        CreatedUserResponseDTO createdUserResponseDTO = new CreatedUserResponseDTO(
                "2fda2949-c2cb-484a-823c-d313561892e2",
                new Date(System.currentTimeMillis()),
                null,
                new Date(System.currentTimeMillis()),
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE2NjUzMzA3NzcsImV4cCI6MTY2NTMzMTI1N30.JNQkiUIidcQgqp4bzZwr7q9sTxlsAX5xwXqKzw6CSkI",
                true
        );
        when(userService.registerUser(userData)).thenReturn(createdUserResponseDTO);

        String requestString = mapper.writeValueAsString(userData);
        mvc.perform(post("/user/create").header("Authorization", "Bearer " + TOKEN).content(requestString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    public void authenticateUser__OK() throws Exception {

        AuthenticateRequest authenticateRequest = new AuthenticateRequest("juan@rodriguez.org", "123456");

        AuthenticateResponse authenticateResponse = new AuthenticateResponse(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE2NjUzMzA3NzcsImV4cCI6MTY2NTMzMTI1N30.JNQkiUIidcQgqp4bzZwr7q9sTxlsAX5xwXqKzw6CSkI"
        );
        UserDetails userDetails =  new User("juan@rodriguez.org", "123456", new ArrayList<>());

        when(jwtUtilService.generateToken(authenticateRequest.getUsername())).thenReturn(TOKEN);
        when(myUserDetailsService.loadUserByUsername("juan@rodriguez.org")).thenReturn(userDetails);

        String requestString = mapper.writeValueAsString(authenticateRequest);
        mvc.perform(post("/user/authenticate").header("Authorization", "Bearer " + TOKEN).content(requestString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
}