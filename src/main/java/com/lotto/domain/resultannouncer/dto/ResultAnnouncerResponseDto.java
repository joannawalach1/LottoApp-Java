package com.lotto.domain.resultannouncer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record ResultAnnouncerResponseDto(ResponseDto responseDto, String message){
}
