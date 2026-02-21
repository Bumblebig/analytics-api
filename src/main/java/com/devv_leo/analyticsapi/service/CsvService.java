package com.devv_leo.analyticsapi.service;

import com.devv_leo.analyticsapi.model.MerchantEvent;
import com.devv_leo.analyticsapi.repository.MerchantEventRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    private final MerchantEventRepository repository;

    public CsvService(MerchantEventRepository repository) {
        this.repository = repository;
    }

    private static final String CSV_FOLDER_PATH = "data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<MerchantEvent> readAllCsvs() {
        List<MerchantEvent> events = new ArrayList<>();

        File folder = new File(CSV_FOLDER_PATH);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (files == null) {
            System.out.println("No CSV files found in " + folder.getAbsolutePath());
            return events;
        }

        System.out.println("Found " + files.length + " CSV files in " + folder.getAbsolutePath());

        for (File file : files) {
            System.out.println("Reading file: " + file.getName());
            events.addAll(readCsv(file));
        }

        System.out.println("Total events read: " + events.size());
        return events;
    }

    private List<MerchantEvent> readCsv(File file) {
        List<MerchantEvent> events = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] line;
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                LocalDateTime eventTimestamp = null;
                if (line[2] != null && !line[2].isBlank()) {
                    try {
                        eventTimestamp = LocalDateTime.parse(line[2], formatter);
                    } catch (Exception e) {
                        System.out.println("Invalid date in file " + file.getName() + ": " + line[2]);
                    }
                } else {
                    continue;
                }

                BigDecimal amount = BigDecimal.ZERO;
                if (line[5] != null && !line[5].isBlank()) {
                    try {
                        amount = new BigDecimal(line[5]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount in file " + file.getName() + ": " + line[5]);
                    }
                }

                MerchantEvent event = new MerchantEvent(
                        line[0], line[1], eventTimestamp, line[3], line[4],
                        amount, line[6], line[7], line[8], line[9]);

                events.add(event);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return events;
    }

    @Transactional
    public void importAllCsvsToDb() {
        List<MerchantEvent> events = readAllCsvs();
        repository.saveAll(events);
        System.out.println("Imported " + events.size() + " events into PostgreSQL");
    }
}