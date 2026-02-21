package com.devv_leo.analyticsapi.repository;

import com.devv_leo.analyticsapi.model.MerchantEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantEventRepository extends JpaRepository<MerchantEvent, String> {
}