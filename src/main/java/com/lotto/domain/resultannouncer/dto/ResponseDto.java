package com.lotto.domain.resultannouncer.dto;


import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
public record ResponseDto(
        String hash,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        LocalDateTime drawDate,
        boolean isWinner) {
}