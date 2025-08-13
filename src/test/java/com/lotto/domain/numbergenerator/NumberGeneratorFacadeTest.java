package com.lotto.domain.numbergenerator;

import com.lotto.domain.numberrceiver.DrawDateGenerator;
import com.lotto.infrastructure.http.numbergenerator.NumberGeneratorFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NumberGeneratorFacadeTest {
    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );

    NumberGeneratorFetcher numberGeneratorFetcher = mock(NumberGeneratorFetcher.class);
    InMemoryNumberGeneratorRepository inMemoryNumberGeneratorRepository = new InMemoryNumberGeneratorRepository();

    NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacade(
            numberGeneratorFetcher,
            new DrawDateGenerator(clock),
            new WinningNumbersValidator(),
            inMemoryNumberGeneratorRepository
    );

    @Test
    void should_return_six_winning_numbers() {
        when(numberGeneratorFetcher.fetchNumbers()).thenReturn(Set.of(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = numberGeneratorFacade.generateWinningNumbers();
        assertThat(winningNumbers.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    void should_throw_exception_when_fetched_numbers_are_less_than_six() {
        when(numberGeneratorFetcher.fetchNumbers()).thenReturn(Set.of(1, 2, 3, 4, 5));
        NotValidDataException notValidData = assertThrows(NotValidDataException.class, () -> numberGeneratorFacade.generateWinningNumbers());
        assertEquals("Incorrect number or range of numbers.", notValidData.getMessage());
    }

    @Test
    void should_throw_exception_when_fetched_numbers_are_greater_than_six() {
        when(numberGeneratorFetcher.fetchNumbers()).thenReturn(Set.of(1, 2, 3, 4, 5, 6, 7));
        NotValidDataException notValidData = assertThrows(NotValidDataException.class, () -> numberGeneratorFacade.generateWinningNumbers());
        assertEquals("Incorrect number or range of numbers.", notValidData.getMessage());
    }
}


