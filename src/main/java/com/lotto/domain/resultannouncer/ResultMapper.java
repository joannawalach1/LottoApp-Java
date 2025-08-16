package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
    public interface ResultMapper {
        ResponseDto mapToDto(ResultResponse resultResponse);
    }
