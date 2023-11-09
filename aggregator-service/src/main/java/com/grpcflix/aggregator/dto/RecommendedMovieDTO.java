package com.grpcflix.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedMovieDTO {

    private String title;

    private int releasedYear;

    private double rating;

}
