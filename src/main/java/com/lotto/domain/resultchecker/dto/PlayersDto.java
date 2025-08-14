package com.lotto.domain.resultchecker.dto;

import lombok.Builder;

@Builder
public record PlayersDto(String message, ResultDto resultDto) {
}
