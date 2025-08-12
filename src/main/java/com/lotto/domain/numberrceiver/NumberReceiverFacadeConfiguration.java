package com.lotto.domain.numberrceiver;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class NumberReceiverFacadeConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public TicketMapper ticketMapper() {
        return Mappers.getMapper(TicketMapper.class);
    }


    @Bean
    public NumberReceiverFacade numberReceiverFacade(NumberReceiverRepository repository, TicketMapper ticketMapper, Clock clock) {
        UserNumberValidator numberValidator = new UserNumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        HashGenerable hashGenerable = new HashGenerator();
        return new NumberReceiverFacade(
                repository,
                numberValidator,
                ticketMapper,
                clock,
                drawDateGenerator,
                hashGenerable
        );
    }
}

