package com.devv_leo.analyticsapi.service;

import com.devv_leo.analyticsapi.repository.MerchantEventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AnalyticsService {

    private final MerchantEventRepository repository;

    public AnalyticsService(MerchantEventRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getTopMerchant() {
        List<Object[]> rows = repository.findTopMerchantByTotalVolume();
        if (rows.isEmpty()) {
            return Collections.emptyMap();
        }
        Object[] row = rows.get(0);
        String merchantId = (String) row[0];
        BigDecimal total = (BigDecimal) row[1];

        Map<String, Object> result = new HashMap<>();
        result.put("merchant_id", merchantId);
        result.put("total_volume", total.setScale(2, RoundingMode.HALF_UP));
        return result;
    }

    public Map<String, Long> getMonthlyActiveMerchants() {
        return Collections.emptyMap();
    }

    public Map<String, Long> getProductAdoption() {
        return Collections.emptyMap();
    }

    public Map<String, Long> getKycFunnel() {
        return Collections.emptyMap();
    }

    public List<Map<String, Object>> getFailureRates() {
        return Collections.emptyList();
    }
}