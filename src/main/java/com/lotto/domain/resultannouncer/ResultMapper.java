package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
    public interface ResultMapper {
        ResponseDto mapToDto(ResultResponse resultResponse);
    }
