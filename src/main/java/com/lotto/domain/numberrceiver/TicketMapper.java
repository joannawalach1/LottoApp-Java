package com.lotto.domain.numberrceiver;


import com.lotto.domain.numberrceiver.dto.TicketDto;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {
    TicketDto toDto(Ticket ticket);
}
