package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberrceiver.DrawDateGenerator;
import com.lotto.infrastructure.http.numbergenerator.NumberGeneratorFetcher;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class NumberGeneratorFacade {
    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );
    private  NumberGeneratorFetcher numberGeneratorFetcher = new NumberGeneratorFetcher();
    private  DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
    private  WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
    private  NumberGeneratorRepository numberGeneratorRepository;

    public WinningNumbers generateWinningNumbers() {
        String id = UUID.randomUUID().toString();
        LocalDateTime nextDrawDate = drawDateGenerator.nextDrawDate(LocalDateTime.now());
        Set<Integer> generatedNumbers = numberGeneratorFetcher.fetchNumbers();
        winningNumbersValidator.validateNumbers(generatedNumbers);
        WinningNumbers winningNumbers = new WinningNumbers(id, nextDrawDate, generatedNumbers);
        numberGeneratorRepository.save(winningNumbers);
        return winningNumbers;
    }

    public WinningNumbers findWinningNumbersByDate(LocalDateTime nextDrawDate) {
        return numberGeneratorRepository.findNumbersByNextDrawDate(nextDrawDate)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Winning numbers not found"));
    }
}
