package com.devv_leo.analyticsapi.repository;

import com.devv_leo.analyticsapi.model.MerchantEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantEventRepository extends JpaRepository<MerchantEvent, String> {
    @Query(value = """
            SELECT merchant_id, SUM(amount) AS total_volume
            FROM merchant_events
            WHERE status = 'SUCCESS'
            GROUP BY merchant_id
            ORDER BY total_volume DESC
            LIMIT 1
            """, nativeQuery = true)
    List<Object[]> findTopMerchantByTotalVolume();

    @Query(value = """
            SELECT to_char(event_timestamp, 'YYYY-MM') AS month,
               COUNT(DISTINCT merchant_id)       AS cnt
            FROM merchant_events
            WHERE status = 'SUCCESS'
            GROUP BY month
            ORDER BY month
            """, nativeQuery = true)
    List<Object[]> findMonthlyActiveMerchants();

    @Query(value = """
            SELECT product, COUNT(DISTINCT merchant_id) AS cnt
            FROM merchant_events
            GROUP BY product
            ORDER BY cnt DESC
            """, nativeQuery = true)
    List<Object[]> findProductAdoption();

    @Query(value = """
            SELECT event_type, COUNT(DISTINCT merchant_id) AS cnt
            FROM merchant_events
            WHERE product = 'KYC'
              AND status = 'SUCCESS'
              AND event_type IN ('DOCUMENT_SUBMITTED', 'VERIFICATION_COMPLETED', 'TIER_UPGRADE')
            GROUP BY event_type
            """, nativeQuery = true)
    List<Object[]> findKycFunnel();

    @Query(value = """
            SELECT product,
               SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END)                  AS failed,
               SUM(CASE WHEN status IN ('SUCCESS', 'FAILED') THEN 1 ELSE 0 END)    AS total
            FROM merchant_events
            GROUP BY product
            """, nativeQuery = true)
    List<Object[]> findFailureStatsByProduct();
}