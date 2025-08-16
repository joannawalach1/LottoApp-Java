package com.lotto.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResultAnnouncerRepository extends MongoRepository<ResultResponse, String> {
    Optional<ResultResponse> findByHash(String hash);

    boolean existsById(String hash);

    Optional<ResultResponse> findById(String hash);
}
