package com.grpcflix.aggregator.service;

import com.grpcflix.aggregator.dto.RecommendedMovieDTO;
import com.grpcflix.aggregator.dto.UserGenreDTO;
import com.tackr.grpcflix.common.Genre;
import com.tackr.grpcflix.movie.MovieSearchRequest;
import com.tackr.grpcflix.movie.MovieSearchResponse;
import com.tackr.grpcflix.movie.MovieServiceGrpc;
import com.tackr.grpcflix.user.UserGenreUpdateRequest;
import com.tackr.grpcflix.user.UserResponse;
import com.tackr.grpcflix.user.UserSearchRequest;
import com.tackr.grpcflix.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;

    public List<RecommendedMovieDTO> getRecommendedUserMovie(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();

        UserResponse userGenre = this.userServiceBlockingStub.getUserGenre(userSearchRequest);

        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userGenre.getGenre()).build();

        MovieSearchResponse movies = this.movieServiceBlockingStub.getMovies(movieSearchRequest);

        return movies.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovieDTO(movieDto.getTitle(), movieDto.getReleaseYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenreDTO userGenre) {
        UserGenreUpdateRequest updateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre()))
                .build();

        UserResponse userResponse = this.userServiceBlockingStub.updateUserGenre(updateRequest);

    }

}
