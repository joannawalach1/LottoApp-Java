package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import org.mapstruct.Mapper;

@Mapper
public interface WinningNumbersMapper {
    WinningNumbersDto toDto(WinningNumbers winningNumbers);
}
