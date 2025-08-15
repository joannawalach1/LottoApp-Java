package com.lotto.domain.numberrceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record TicketDto(String id, Set<Integer> userNumbers, LocalDateTime ticketCreatedAt) {
}