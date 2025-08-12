package com.lotto.domain.numberrceiver.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record TicketDto(Set<Integer> userNumbers) {
}