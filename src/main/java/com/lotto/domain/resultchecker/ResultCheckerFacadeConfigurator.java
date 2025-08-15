package com.lotto.domain.resultchecker;

import com.lotto.domain.numbergenerator.NumberGeneratorFacade;
import com.lotto.domain.numberrceiver.*;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

public class ResultCheckerFacadeConfigurator {
    private NumberReceiverRepository numberReceiverRepository;
    private NumberGeneratorFacade numberGeneratorFacade;
    private WinnersRetriever winnersRetriever;
    private NumberReceiverFacade numberReceiverFacade;

    @Bean
    public ResultCheckerFacade resultCheckerFacade(PlayerRepository repository, WinnersMapper winnersMapper, Clock clock) {
        return new ResultCheckerFacade(
                numberReceiverFacade,
                repository,
                numberGeneratorFacade,
                winnersRetriever,
                winnersMapper
        );
    }
}
