package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberrceiver.DrawDateGenerator;
import com.lotto.infrastructure.http.numbergenerator.NumberGeneratorFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Configuration
public class NumberGenerateFacadeConfiguration {

    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );
    private WinningNumbersMapper winningNumberMapper;

    @Bean
    public NumberGeneratorFacade numberGeneratorFacade(NumberGeneratorFetcher numberGeneratorFetcher, NumberGeneratorRepository numberGeneratorRepository) {
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new NumberGeneratorFacade(
                numberGeneratorFetcher,
                drawDateGenerator,
                winningNumbersValidator,
                numberGeneratorRepository,
                winningNumberMapper);
    }
}
