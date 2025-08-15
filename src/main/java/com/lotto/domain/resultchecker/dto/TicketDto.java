package com.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record TicketDto(String id, Set<Integer> userNumbers, LocalDateTime now
) {
}
