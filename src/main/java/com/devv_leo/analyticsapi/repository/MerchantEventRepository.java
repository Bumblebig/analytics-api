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
}