package com.lotto.domain.numberrceiver;


import com.lotto.domain.numberrceiver.dto.TicketDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TicketMapper {
    List<Ticket> toDtos(List<TicketDto> ticket);
    TicketDto toDto(Ticket ticket);
}
