package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultchecker.ResultCheckerFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ResultAnnouncerFacadeConfiguration {

    private ResultCheckerFacade resultCheckerFacade;

    @Bean
    public ResultAnnouncerFacade resultAnnouncerFacade(ResultAnnouncerRepository resultAnnouncerRepository, ResultMapper resultMapper, Clock clock) {
        return new ResultAnnouncerFacade(
                resultAnnouncerRepository,
                resultCheckerFacade,
                resultMapper,
                clock
        );
    }
}

