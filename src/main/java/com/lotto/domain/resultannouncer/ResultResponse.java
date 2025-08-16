package com.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@AllArgsConstructor
@Builder
public class ResultResponse {
    String hash;
    Set<Integer> numbers;
    Set<Integer> hitNumbers;
    LocalDateTime drawDate;
    boolean isWinner;
}
