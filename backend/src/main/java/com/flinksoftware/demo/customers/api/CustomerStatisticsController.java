package com.flinksoftware.demo.customers.api;

import com.flinksoftware.demo.customers.StatisticService;
import com.flinksoftware.demo.customers.dto.Statistics;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats/offers-customers")
@Slf4j
@Tag(name = "Statistics", description = "Statistic operations")
public class CustomerStatisticsController {

    private final StatisticService statisticService;

    public CustomerStatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<Statistics> getAll(Authentication authentication) {
        final Statistics statistic = statisticService.getStatistic(getTenantId(authentication));
        return ResponseEntity.ok(statistic);
    }

    private static String getTenantId(Authentication authentication) {
        final JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        return jwtAuth.getToken().getClaimAsString("tenant");
    }
}
