package com.lotto.domain.numberrceiver;

import com.lotto.domain.numberrceiver.dto.TicketDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/tickets")
public class NumberReceiverController {
    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @PostMapping
    public ResponseEntity<TicketDto> inputUserNumbers(@RequestBody Set<Integer> userNumbers) {
        TicketDto numbersFromUser = numberReceiverFacade.getNumbersFromUser(userNumbers);
        return ResponseEntity.status(HttpStatus.OK).body(numbersFromUser);
    }
}
