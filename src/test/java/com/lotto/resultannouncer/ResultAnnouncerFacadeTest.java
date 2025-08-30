package com.lotto.resultannouncer;

import com.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import com.lotto.domain.resultannouncer.ResultMapper;
import com.lotto.domain.resultannouncer.dto.ResponseDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static com.lotto.domain.resultannouncer.MessageResponse.WAIT_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultAnnouncerFacadeTest {

    InMemoryResultAnnouncerRepository responseRepository = new InMemoryResultAnnouncerRepository();
    ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
    ResultMapper resultMapper = Mappers.getMapper(ResultMapper.class);
    ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(responseRepository, resultCheckerFacade, resultMapper, Clock.systemUTC());

    @Test
    public void it_should_return_win_message_if_hash_exists() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String hash = "123";
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.generateResult(hash)).thenReturn(resultDto);

        ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultAnnouncerResponseDto1.responseDto().hash();
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(
                resultAnnouncerResponseDto.responseDto()
                , "Congratulations, you won!");
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_has_does_not_exists_message_if_hash_exists() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String hash = "123";
        ResultDto resultDto = ResultDto.builder()
                .hash("456")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.generateResult(hash)).thenReturn(resultDto);

        ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultAnnouncerResponseDto1.responseDto().hash();
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(
                resultAnnouncerResponseDto.responseDto()
                , "Given ticket does not exist");
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_lose_message_if_hash_exists() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        String hash = "123";
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(null)
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        when(resultCheckerFacade.generateResult(hash)).thenReturn(resultDto);

        ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultAnnouncerResponseDto1.responseDto().hash();
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(
                resultAnnouncerResponseDto.responseDto()
                , "No luck, try again!");
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }


    @Test
    public void it_should_return_response_with_wait_message_if_date_is_before_announcement_time() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        String hash = "123";
        Clock clock = Clock.fixed(LocalDateTime.of(2022, 12, 17, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacade(responseRepository, resultCheckerFacade, resultMapper, clock);
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.generateResult(hash)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(responseDto, "Results are being calculated, please come back later");
        assertThat(resultAnnouncerResponseDto).isEqualTo(expectedResult);
    }
}
