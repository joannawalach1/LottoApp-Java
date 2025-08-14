package com.lotto.domain.numberrceiver;

import com.lotto.domain.numberrceiver.dto.TicketDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NumberReceiverRepository extends MongoRepository<Ticket, String> {
    List<TicketDto> findTicketsByTicketCreatedAt(LocalDateTime now);
}
