package com.flinksoftware.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

@Configuration
public class TimeZoneConfiguration {
    private static final ZoneId AMSTERDAM = ZoneId.of("Europe/Amsterdam");

    /**
     * The TimeZone for any {@link java.time.LocalDate}s in the system.
     */
    @Bean
    public ZoneId appTimeZone() {
        return AMSTERDAM;
    }
}
