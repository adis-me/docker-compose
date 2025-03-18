package com.flinksoftware.demo;

import com.flinksoftware.demo.accounts.dto.Country;
import com.flinksoftware.demo.customers.dto.Customer;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TestCaseUtil {

    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Amsterdam");
    private static final LocalDate TODAY = LocalDate.of(2013, 11, 8);
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalDate TOMORROW = TODAY.plusDays(1);

    public static ZoneId zoneId() {
        return ZONE_ID;
    }

    public static Clock clockAt(int hours, int minutes) {
        return Clock.fixed(instantTodayAt(hours, minutes), zoneId());
    }

    public static LocalDate today() {
        return TODAY;
    }

    public static OffsetDateTime todayAt(int hours, int minutes) {
        return TODAY.atTime(hours, minutes).atZone(ZONE_ID).toOffsetDateTime();
    }

    public static Instant instantTodayAt(int hours, int minutes) {
        return TODAY.atTime(hours, minutes).atZone(ZONE_ID).toInstant();
    }

    public static LocalDate yesterday() {
        return YESTERDAY;
    }

    public static OffsetDateTime yesterdayAt(int hours, int minutes) {
        return YESTERDAY.atTime(hours, minutes).atZone(ZONE_ID).toOffsetDateTime();
    }

    public static Instant instantYesterdayAt(int hours, int minutes) {
        return YESTERDAY.atTime(hours, minutes).atZone(ZONE_ID).toInstant();
    }

    public static LocalDate tomorrow() {
        return TOMORROW;
    }

    public static OffsetDateTime tomorrowAt(int hours, int minutes) {
        return TOMORROW.atTime(hours, minutes).atZone(ZONE_ID).toOffsetDateTime();
    }

    public static Instant instantTomorrowAt(int hours, int minutes) {
        return TOMORROW.atTime(hours, minutes).atZone(ZONE_ID).toInstant();
    }

    public static Customer createMinimalCustomer() {
        return Customer.builder()
                .name("Test customer")
                .build();
    }

    public static Customer createCustomer() {
        return createMinimalCustomer().toBuilder()
                .name("Test customer")
                .address("Jupiter 1")
                .postalCode("0001 JU")
                .city("Unit city")
                .country(Country.builder()
                        .id("NL")
                        .build())
                .email("test@junit.com")
                .build();
    }
}
