package com.flinksoftware.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SuppressWarnings("PMD.ClassNamingConventions")
public final class CurrencyUtil {

    private CurrencyUtil() {
    }

    public static Pair<String, BigDecimal> parseAmount(String amountWithCurrency) {
        String currency = "";
        BigDecimal amount = BigDecimal.ZERO;
        final Pattern pattern = Pattern.compile("\\d+((.|,)\\d+)((.|,)\\d+)?");
        final Matcher matcher = pattern.matcher(amountWithCurrency);
        if (matcher.find()) {
            String parsedAmount = matcher.group(0);

            // parse currency code
            currency = amountWithCurrency
                    .replace(parsedAmount, "")
                    .replace("\u00a0", "")
                    .trim();

            parsedAmount = parsedAmount.replace(",", ".");
            final int lastDot = parsedAmount.lastIndexOf('.');
            if (lastDot != -1) {
                parsedAmount = parsedAmount.substring(0, lastDot) + parsedAmount.substring(lastDot);
            } else if (lastDot == parsedAmount.length() - 1) {
                parsedAmount = "0";
            }

            try {
                amount = new BigDecimal(parsedAmount);
            } catch (Exception ignored) {
                log.error("Failed to parse {}, will return 0", amountWithCurrency);
                return Pair.of("", BigDecimal.ZERO);
            }
        }

        return Pair.of(currency, amount);
    }
}
