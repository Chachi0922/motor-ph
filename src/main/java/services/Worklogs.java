package services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import models.WorkLogEntry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * The Worklogs class provides utility methods for processing work log data from CSV files.
 */
public class Worklogs {
    /**
     * Reads work log entries from a CSV file and displays them to the console.
     * This method uses OpenCSV library to parse the CSV data into WorkLogEntry objects,
     * utilizing header column name mapping for automatic field population.
     *
     * @param csvFile The path to the CSV file containing work log data
     * @throws RuntimeException If there is an error reading or parsing the CSV file
     *                         (wrapped from underlying IOException or parsing exceptions)
     */
    public void readAndDisplayCSV(String csvFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            HeaderColumnNameMappingStrategy<WorkLogEntry> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(WorkLogEntry.class);
            CsvToBean<WorkLogEntry> csvToBean = new CsvToBeanBuilder<WorkLogEntry>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<WorkLogEntry> workLogEntries = csvToBean.parse();
            for (WorkLogEntry entry : workLogEntries) {
                System.out.println(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}