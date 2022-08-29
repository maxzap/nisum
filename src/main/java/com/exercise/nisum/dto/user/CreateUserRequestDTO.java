package com.exercise.nisum.dto.user;

import com.exercise.nisum.dto.contact.ContactDataDTO;
import com.exercise.nisum.validations.EmailType;
import com.exercise.nisum.validations.PasswordType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank(message = "Debe especificar un nombre")
    @JsonProperty(value = "name")
    String name;

    @NotBlank(message = "Debe especificar el email")
    @EmailType
    @JsonProperty(value = "email")
    String email;

    @NotBlank(message = "Debe especificar la contraseña")
    @PasswordType
    @JsonProperty(value = "password")
    String password;

    @JsonProperty(value = "phones")
    List<ContactDataDTO> phones;


}
