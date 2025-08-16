package com.lotto.domain.resultchecker;

import com.lotto.domain.numberrceiver.Ticket;
import com.lotto.domain.numberrceiver.dto.TicketDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WinnersMapper {
    List<Ticket> toDtos(List<TicketDto> ticketsByNextDrawDate);
}
