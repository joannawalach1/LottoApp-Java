package com.lotto.infrastructure.http.numbergenerator;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class NumberGeneratorFetcher {
    private static final Logger log = LoggerFactory.getLogger(NumberGeneratorFetcher.class);

    private final RestTemplate restTemplate;

    public NumberGeneratorFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<Integer> fetchNumbers() {
        String url = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=49&count=6";
        Integer[] numbersArray = restTemplate.getForObject(url, Integer[].class);
        if (numbersArray == null) {
            return Collections.emptySet();
        }
        log.info("Fetching numbers from: {}", url);
        return new HashSet<>(Arrays.asList(numbersArray));
    }
}
