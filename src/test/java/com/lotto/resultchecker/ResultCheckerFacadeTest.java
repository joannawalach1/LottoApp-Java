package com.lotto.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberrceiver.NumberReceiverFacade;
import com.lotto.domain.numberrceiver.Ticket;
import com.lotto.domain.numberrceiver.TicketMapper;
import com.lotto.domain.numberrceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ResultCheckerFacadeTest {
    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );
    private NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    private NumberGeneratorFacade numberGeneratorFacade= mock(NumberGeneratorFacade.class);
    private InMemoryPlayerRepository inMemoryPlayerRepository;
    private WinnersRetriever winnerRetriever;
    private WinnersMapper winnersMapper = Mappers.getMapper(WinnersMapper.class);

    ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(
            numberReceiverFacade,
            new InMemoryPlayerRepository(),
            numberGeneratorFacade,
            new WinnersRetriever(),
            winnersMapper
    );
    private TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    @Test
    void should_generate_winners_and_save_players() {
        List<TicketDto> ticketDtos = List.of(new TicketDto("1id", Set.of(1,2,3,4,5,6), LocalDateTime.now(clock)));
        List<Ticket> tickets = List.of(new Ticket("1id", Set.of(1,2,3,4,5,6), LocalDateTime.now(clock)));
        WinningNumbersDto winningNumbersDto = new WinningNumbersDto(Set.of(1,2,3,4,5,6));
        List<Players> expectedPlayers = List.of(new Players("1id", Set.of(1,2,3,4,5,6), Set.of(1,2,3,4,5,6), LocalDateTime.now(clock), true));
        when(numberReceiverFacade.getTicketsByNextDrawDate(any())).thenReturn(ticketDtos);
        when(numberGeneratorFacade.generateWinningNumbers()).thenReturn(winningNumbersDto);
        List<Players> result = resultCheckerFacade.generateWinners();
        assertThat(result).isEqualTo(expectedPlayers);
    }
}
