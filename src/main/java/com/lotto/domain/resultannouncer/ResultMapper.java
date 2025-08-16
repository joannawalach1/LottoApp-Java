package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import org.mapstruct.Mapper;

@Mapper
    public interface ResultMapper {
        ResponseDto mapToDto(ResultResponse resultResponse);
    }
