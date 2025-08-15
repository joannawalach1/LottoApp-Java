package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberrceiver.NumberReceiverFacade;
import com.lotto.domain.numberrceiver.Ticket;
import com.lotto.domain.numberrceiver.dto.TicketDto;
import com.lotto.domain.resultchecker.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
public class ResultCheckerFacade {
    private static final Logger log = LoggerFactory.getLogger(ResultCheckerFacade.class);
    private final NumberReceiverFacade numberReceiverFacade;
    private final PlayerRepository playerRepository;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final WinnersRetriever winnerRetriever;
    private final  WinnersMapper winnersMapper;

    public ResultCheckerFacade(NumberReceiverFacade numberReceiverFacade, PlayerRepository playerRepository, NumberGeneratorFacade numberGeneratorFacade, WinnersRetriever winnerRetriever, WinnersMapper winnersMapper) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.playerRepository = playerRepository;
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.winnerRetriever = winnerRetriever;
        this.winnersMapper = winnersMapper;
    }


    public List<Players> generateWinners() {
        List<TicketDto> ticketsByNextDrawDate = numberReceiverFacade.getTicketsByNextDrawDate(LocalDateTime.now());
        List<Ticket> tickets = winnersMapper.toDtos(ticketsByNextDrawDate);
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        List<Players> players = winnerRetriever.calculateWinners(tickets, winningNumbers);
        log.info(players.toString());
        playerRepository.saveAll(players);
        return players;
    }

    public ResultDto generateResult(String hash) {
        Players players = playerRepository.findById(hash)
                .orElseThrow(() -> new RuntimeException("hhhhhhhhhhhhhhhhhhhhhhhh"));
        return new ResultDto(hash, players.userNumbers(), players.hitNumbers(), players.drawDate(), players.isWinner());
    }
}
