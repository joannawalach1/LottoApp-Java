package com.lotto.numberrceiver;

import com.lotto.domain.numberrceiver.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberReceiverFacadeTest {
    Clock clock = Clock.fixed(
            LocalDateTime.of(2025, 5, 21, 12, 0, 0)
                    .toInstant(ZoneOffset.UTC),
            ZoneId.systemDefault()
    );
    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new InMemoryNumberReceiverRepository(),
            new UserNumberValidator(),
            Mappers.getMapper(TicketMapper.class),
            clock,
            new DrawDateGenerator(clock),
            new HashGenerator()
    );

    @Test
    void should_return_ticket_if_users_gave_six_numbers() {
        //given
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6);
        //when
        Ticket result = numberReceiverFacade.getNumbersFromUser(userNumbers);
        //then
        assertEquals(result.userNumbers().size(), userNumbers.size());
    }

    @Test
    void should_return_exception_if_users_gave_less_than_six_numbers() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5);
        //when
        //then
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, () ->
                numberReceiverFacade.getNumbersFromUser(userNumbers)
        );
        assertEquals("Incorrect number or range of numbers.", invalidDataException.getMessage());
    }

    @Test
    void should_return_exception_if_users_gave_more_than_six_numbers() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 6, 7);
        //when
        //then
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, () ->
                numberReceiverFacade.getNumbersFromUser(userNumbers)
        );
        assertEquals("Incorrect number or range of numbers.", invalidDataException.getMessage());
    }

    @Test
    void should_return_exception_if_even_one_number_is_out_of_range() {
        Set<Integer> userNumbers = Set.of(1, 2, 3, 4, 5, 1000);
        //when
        //then
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, () ->
                numberReceiverFacade.getNumbersFromUser(userNumbers)
        );
        assertEquals("Incorrect number or range of numbers.", invalidDataException.getMessage());
    }
}