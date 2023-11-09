package com.grpcflix.aggregator.controller;


import com.grpcflix.aggregator.dto.RecommendedMovieDTO;
import com.grpcflix.aggregator.dto.UserGenreDTO;
import com.grpcflix.aggregator.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private UserMovieService userMovieService;

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovieDTO> recommendedMovies(@PathVariable String loginId) {
        return this.userMovieService.getRecommendedUserMovie(loginId);
    }

    @PutMapping("/user")
    public void userGenre(@RequestBody UserGenreDTO userGenre) {
        this.userMovieService.setUserGenre(userGenre);
    }
}
