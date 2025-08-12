package com.lotto.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NumberGeneratorRepository extends MongoRepository<WinningNumbers, String> {
    Optional<WinningNumbers> findNumbersByNextDrawDate(LocalDateTime nextDrawDate);
}
