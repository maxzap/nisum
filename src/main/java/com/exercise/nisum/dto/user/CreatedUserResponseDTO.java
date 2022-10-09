package com.exercise.nisum.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedUserResponseDTO {

    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "created")
    Date created;

    @JsonProperty(value = "modified")
    Date modified;

    @JsonProperty(value = "last_login")
    Date last_login;

    @JsonProperty(value = "token")
    String token;

    @JsonProperty(value = "isActive")
    Boolean isActive;

}
