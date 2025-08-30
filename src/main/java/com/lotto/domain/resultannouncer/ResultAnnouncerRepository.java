package com.lotto.domain.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResultAnnouncerRepository extends MongoRepository<ResultResponse, String> {
    ResultResponse findByHash(String hash);

    ResultResponse findByAnId(String id);

    boolean existsById(String hash);

}
