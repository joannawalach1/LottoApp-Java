package com.lotto.domain.numberrceiver;

import java.util.UUID;

public class HashGenerator implements HashGenerable{
    @Override
    public String generateHash() {
        return UUID.randomUUID().toString();
    }
}
