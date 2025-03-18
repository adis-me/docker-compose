package com.flinksoftware.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfiguration {
    /**
     * The clock that is used system-wide.
     * Including the {@link com.flinksoftware.demo.config.TimeZoneConfiguration#appTimeZone()}  system TimeZone}.
     */
    @Bean
    public Clock flinkClock(ZoneId zone) {
        return Clock.system(zone);
    }
}
