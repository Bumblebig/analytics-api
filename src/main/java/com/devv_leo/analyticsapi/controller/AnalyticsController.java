package com.devv_leo.analyticsapi.controller;

import com.devv_leo.analyticsapi.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/top-merchant")
    public Map<String, Object> topMerchant() {
        return service.getTopMerchant();
    }

    @GetMapping("/monthly-active-merchants")
    public Map<String, Long> monthlyActiveMerchants() {
        return service.getMonthlyActiveMerchants();
    }

    @GetMapping("/product-adoption")
    public Map<String, Long> productAdoption() {
        return service.getProductAdoption();
    }

    @GetMapping("/kyc-funnel")
    public Map<String, Long> kycFunnel() {
        return service.getKycFunnel();
    }

    @GetMapping("/failure-rates")
    public List<Map<String, Object>> failureRates() {
        return service.getFailureRates();
    }
}