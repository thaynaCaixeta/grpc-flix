package com.tackr.user.service;

import com.tackr.grpcflix.common.Genre;
import com.tackr.grpcflix.user.UserGenreUpdateRequest;
import com.tackr.grpcflix.user.UserResponse;
import com.tackr.grpcflix.user.UserSearchRequest;
import com.tackr.grpcflix.user.UserServiceGrpc;
import com.tackr.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired

    private UserRepository repository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();

        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()))
                            .setLoginId(user.getLogin());
                });

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();

        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName())
                            .setGenre(request.getGenre())
                            .setLoginId(user.getLogin());
                });

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
