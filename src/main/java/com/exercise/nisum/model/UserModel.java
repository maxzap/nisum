package com.exercise.nisum.model;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_data",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"email"})
)
@Data
public class UserModel {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(columnDefinition = "VARCHAR(200)", name = "token")
    private String token;

    @Column(nullable = false, name = "creation_date")
    private LocalDateTime creationDate;

    @Column(nullable = true, name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(nullable = true, name = "last_login_date")
    private LocalDateTime lastLogin;

}
