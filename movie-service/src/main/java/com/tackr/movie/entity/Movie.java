package com.tackr.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Movie {

    @Id
    private int id;

    private String title;

    private int year;

    private double rating;

    private String genre;
}
