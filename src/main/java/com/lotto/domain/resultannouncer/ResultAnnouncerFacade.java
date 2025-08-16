package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.lotto.domain.resultannouncer.MessageResponse.*;

public class ResultAnnouncerFacade {
    private static final LocalTime RESULTS_ANNOUNCEMENT_TIME = LocalTime.of(12, 5);

    private final ResultAnnouncerRepository resultAnnouncerRepository;
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResultMapper resultMapper;
    private final Clock clock;

    public ResultAnnouncerFacade(ResultAnnouncerRepository resultAnnouncerRepository,
                                 ResultCheckerFacade resultCheckerFacade,
                                 ResultMapper resultMapper,
                                 Clock clock) {
        this.resultAnnouncerRepository = resultAnnouncerRepository;
        this.resultCheckerFacade = resultCheckerFacade;
        this.resultMapper = resultMapper;
        this.clock = clock;
    }

    public ResultAnnouncerResponseDto checkResult(String hash) {
        Optional<ResultResponse> cachedResult = resultAnnouncerRepository.findById(hash);
        if (cachedResult.isPresent()) {
            return new ResultAnnouncerResponseDto(
                    resultMapper.mapToDto(cachedResult.get()),
                    ALREADY_CHECKED.info
            );
        }

        ResultDto playerResult = resultCheckerFacade.generateResult(hash);
        if (playerResult == null) {
            return new ResultAnnouncerResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        }

        ResponseDto responseDto = buildResponseDto(playerResult);
        resultAnnouncerRepository.save(buildResponse(responseDto));

        if (!isAfterResultAnnouncementTime(playerResult)) {
            return new ResultAnnouncerResponseDto(responseDto, WAIT_MESSAGE.info);
        }

        return playerResult.isWinner()
                ? new ResultAnnouncerResponseDto(responseDto, WIN_MESSAGE.info)
                : new ResultAnnouncerResponseDto(responseDto, LOSE_MESSAGE.info);
    }

    private ResultResponse buildResponse(ResponseDto responseDto) {
        return ResultResponse.builder()
                .hash(responseDto.hash())
                .numbers(responseDto.numbers())
                .hitNumbers(responseDto.hitNumbers())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .build();
    }

    private ResponseDto buildResponseDto(ResultDto resultDto) {
        return ResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .build();
    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = LocalDateTime.of(resultDto.drawDate().toLocalDate(), RESULTS_ANNOUNCEMENT_TIME); //
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }
}