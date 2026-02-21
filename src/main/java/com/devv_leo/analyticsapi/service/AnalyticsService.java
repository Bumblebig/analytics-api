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
        List<Object[]> rows = repository.findMonthlyActiveMerchants();
        Map<String, Long> result = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String month = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            result.put(month, count);
        }

        return result;
    }

    public Map<String, Long> getProductAdoption() {
        List<Object[]> rows = repository.findProductAdoption();
        Map<String, Long> result = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String product = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            result.put(product, count);
        }

        return result;
    }

    public Map<String, Long> getKycFunnel() {
        List<Object[]> rows = repository.findKycFunnel();
        Map<String, Long> result = new HashMap<>();

        result.put("documents_submitted", 0L);
        result.put("verifications_completed", 0L);
        result.put("tier_upgrades", 0L);

        for (Object[] row : rows) {
            String eventType = (String) row[0];
            Long count = ((Number) row[1]).longValue();

            switch (eventType) {
                case "DOCUMENT_SUBMITTED" -> result.put("documents_submitted", count);
                case "VERIFICATION_COMPLETED" -> result.put("verifications_completed", count);
                case "TIER_UPGRADE" -> result.put("tier_upgrades", count);
                default -> {
                }
            }
        }

        return result;
    }

    public List<Map<String, Object>> getFailureRates() {
        List<Object[]> rows = repository.findFailureStatsByProduct();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            String product = (String) row[0];
            long failed = ((Number) row[1]).longValue();
            long total = ((Number) row[2]).longValue();

            if (total == 0) {
                continue;
            }

            BigDecimal failedBd = BigDecimal.valueOf(failed);
            BigDecimal totalBd = BigDecimal.valueOf(total);

            BigDecimal rate = failedBd
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalBd, 1, RoundingMode.HALF_UP);

            Map<String, Object> map = new HashMap<>();
            map.put("product", product);
            map.put("failure_rate", rate);
            result.add(map);
        }

        result.sort((a, b) -> {
            BigDecimal ra = (BigDecimal) a.get("failure_rate");
            BigDecimal rb = (BigDecimal) b.get("failure_rate");
            return rb.compareTo(ra);
        });

        return result;
    }
}