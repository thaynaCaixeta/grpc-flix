package com.tackr.movie.service;

import com.tackr.grpcflix.movie.MovieDto;
import com.tackr.grpcflix.movie.MovieSearchRequest;
import com.tackr.grpcflix.movie.MovieSearchResponse;
import com.tackr.grpcflix.movie.MovieServiceGrpc;
import com.tackr.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private MovieRepository repository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        String requestGenre = request.getGenre().toString();

        List<MovieDto> movieDTOList = this.repository.getMovieByGenreOrderByYearDesc(requestGenre)
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setReleaseYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build())
                .collect(Collectors.toList());

        MovieSearchResponse response = MovieSearchResponse.newBuilder().addAllMovie(movieDTOList).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
