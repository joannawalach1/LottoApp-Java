package com.lotto.domain.numberrceiver;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record Ticket(String id, Set<Integer> userNumbers, LocalDateTime ticketCreatedAt) {
}
