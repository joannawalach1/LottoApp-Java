package com.lotto.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record Players(String hash, Set<Integer> userNumbers, Set<Integer> hitNumbers, LocalDateTime drawDate, boolean isWinner) {
}