package com.lotto.infrastructure.http.numbergenerator;

import com.lotto.domain.numbergenerator.WinningNumbers;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NumberGeneratorFetcher {
private RestTemplate restTemplate;
    public Set<Integer> fetchNumbers() {
        String url = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=49&count=6";
        Integer[] numbersArray = restTemplate.getForObject(url, Integer[].class);
        if (numbersArray == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(Arrays.asList(numbersArray));
    }
}
