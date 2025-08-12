package com.lotto.domain.numberrceiver;

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
    public ResponseEntity<Ticket> inputUserNumbers(@RequestBody Set<Integer> userNumbers) {
        Ticket numbersFromUser = numberReceiverFacade.getNumbersFromUser(userNumbers);
        return ResponseEntity.status(HttpStatus.OK).body(numbersFromUser);
    }
}
