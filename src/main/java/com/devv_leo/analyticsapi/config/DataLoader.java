package com.devv_leo.analyticsapi.config;

import com.devv_leo.analyticsapi.repository.MerchantEventRepository;
import com.devv_leo.analyticsapi.service.CsvService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CsvService csvService;
    private final MerchantEventRepository repository;

    public DataLoader(CsvService csvService, MerchantEventRepository repository) {
        this.csvService = csvService;
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            csvService.importAllCsvsToDb();
        } else {
            System.out.println("DB already has data, skipping CSV import.");
        }
    }
}