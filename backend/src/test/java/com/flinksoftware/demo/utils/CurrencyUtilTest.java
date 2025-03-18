package com.flinksoftware.demo.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyUtilTest {

    @Test
    void givenRandomString_whenParseAmount_thenExpectDefault() {
        final String given = "I am 5 years old";

        final Pair<String, BigDecimal> result = CurrencyUtil.parseAmount(given);

        assertThat(result).isEqualTo(Pair.of("", BigDecimal.ZERO));
    }

    @Test
    void givenValidAmountWithoutCurrency_whenParseAmount_thenExpectAmount() {
        final String given = "127.99";

        final Pair<String, BigDecimal> result = CurrencyUtil.parseAmount(given);

        assertThat(result).isEqualTo(Pair.of("", new BigDecimal("127.99")));
    }

    @Test
    void givenVariousValidCurrencyWithAmounts_whenParseAmount_thenExpectParsed() {
        final List<String> given = List.of("EURO 5.67",
                "€5.67",
                "€ 5,67",
                "€ 3.07",
                "€ 3.03",
                "€\t5,67",
                "€ \t 5,67");

        final List<Pair<String, BigDecimal>> results = given.stream()
                .map(CurrencyUtil::parseAmount)
                .toList();

        assertThat(results).containsExactly(
                Pair.of("EURO", new BigDecimal("5.67")),
                Pair.of("€", new BigDecimal("5.67")),
                Pair.of("€", new BigDecimal("5.67")),
                Pair.of("€", new BigDecimal("3.07")),
                Pair.of("€", new BigDecimal("3.03")),
                Pair.of("€", new BigDecimal("5.67")),
                Pair.of("€", new BigDecimal("5.67"))
        );
    }
}