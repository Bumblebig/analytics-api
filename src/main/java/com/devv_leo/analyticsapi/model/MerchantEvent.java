package com.devv_leo.analyticsapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_events")
public class MerchantEvent {

    @Id
    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;

    @Column(name = "product")
    private String product;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "amount", scale = 2, precision = 19)
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @Column(name = "channel")
    private String channel;

    @Column(name = "region")
    private String region;

    @Column(name = "merchant_tier")
    private String merchantTier;

    protected MerchantEvent() {
        // TODO: for JPA
    }

    public MerchantEvent(String eventId, String merchantId, LocalDateTime eventTimestamp, String product,
            String eventType, BigDecimal amount, String status, String channel,
            String region, String merchantTier) {
        this.eventId = eventId;
        this.merchantId = merchantId;
        this.eventTimestamp = eventTimestamp;
        this.product = product;
        this.eventType = eventType;
        this.amount = amount;
        this.status = status;
        this.channel = channel;
        this.region = region;
        this.merchantTier = merchantTier;
    }

    public String getEventId() {
        return eventId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public String getProduct() {
        return product;
    }

    public String getEventType() {
        return eventType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getChannel() {
        return channel;
    }

    public String getRegion() {
        return region;
    }

    public String getMerchantTier() {
        return merchantTier;
    }

    // setters
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setMerchantTier(String merchantTier) {
        this.merchantTier = merchantTier;
    }
}