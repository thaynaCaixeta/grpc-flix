package com.tackr.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class User {

    @Id
    private String login;

    private String name;

    private String genre;

    private User user;

}
