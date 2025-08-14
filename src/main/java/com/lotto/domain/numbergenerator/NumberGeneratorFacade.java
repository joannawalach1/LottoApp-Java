package com.lotto.domain.numbergenerator;

import com.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.lotto.domain.numberrceiver.DrawDateGenerator;
import com.lotto.infrastructure.http.numbergenerator.NumberGeneratorFetcher;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

public class NumberGeneratorFacade {
    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );
    private final NumberGeneratorFetcher numberGeneratorFetcher;
    private final DrawDateGenerator drawDateGenerator;
    private final WinningNumbersValidator winningNumbersValidator;
    private final NumberGeneratorRepository numberGeneratorRepository;
    private final  WinningNumbersMapper winningNumberMapper;

    public NumberGeneratorFacade(NumberGeneratorFetcher numberGeneratorFetcher, DrawDateGenerator drawDateGenerator, WinningNumbersValidator winningNumbersValidator, NumberGeneratorRepository numberGeneratorRepository, WinningNumbersMapper winningNumberMapper) {
        this.numberGeneratorFetcher = numberGeneratorFetcher;
        this.drawDateGenerator = drawDateGenerator;
        this.winningNumbersValidator = winningNumbersValidator;
        this.numberGeneratorRepository = numberGeneratorRepository;
        this.winningNumberMapper = winningNumberMapper;
    }

    public WinningNumbersDto generateWinningNumbers() {
        String id = UUID.randomUUID().toString();
        LocalDateTime nextDrawDate = drawDateGenerator.nextDrawDate(LocalDateTime.now());
        Set<Integer> generatedNumbers = numberGeneratorFetcher.fetchNumbers();
        winningNumbersValidator.validateNumbers(generatedNumbers);
        WinningNumbers winningNumbers = new WinningNumbers(id, nextDrawDate, generatedNumbers);
        numberGeneratorRepository.save(winningNumbers);
        WinningNumbersDto winningNumbersDto = winningNumberMapper.toDto(winningNumbers);
        return winningNumbersDto;
    }

    public WinningNumbers findWinningNumbersByDate(LocalDateTime nextDrawDate) {
        return numberGeneratorRepository.findNumbersByNextDrawDate(nextDrawDate)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
    }
}
