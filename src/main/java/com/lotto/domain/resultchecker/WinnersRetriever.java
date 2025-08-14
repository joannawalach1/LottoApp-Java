package com.lotto.domain.resultchecker;

import com.lotto.domain.numberrceiver.Ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WinnersRetriever {
    public List<Players> calculateWinners(List<Ticket> tickets, Set<Integer> winningNumbers) {
        List<Players> winners = new ArrayList<>();
        for (Ticket ticket: tickets) {
            Set<Integer> ticketNumbers = new HashSet<>(ticket.userNumbers());
            ticketNumbers.retainAll(winningNumbers);
            int matches = ticketNumbers.size();
            if (matches >= 3) {
                winners.add(winners.get(0));
            }

        }
        return winners;
    }
}
