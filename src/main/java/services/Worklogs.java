package services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import models.WorkLogEntry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Worklogs class provides utility methods for processing work log data from CSV files.
 */
public class Worklogs {

    // Logger instance using java.util.logging
    private static final Logger logger = Logger.getLogger(Worklogs.class.getName());

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
        // Log the start of the CSV reading process
        logger.log(Level.INFO, "Reading work log entries from CSV file: {0}", csvFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            // Log successful file opening
            logger.log(Level.FINE, "Successfully opened CSV file: {0}", csvFile);

            // Set up the mapping strategy for OpenCSV
            HeaderColumnNameMappingStrategy<WorkLogEntry> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(WorkLogEntry.class);

            // Parse the CSV file into WorkLogEntry objects
            CsvToBean<WorkLogEntry> csvToBean = new CsvToBeanBuilder<WorkLogEntry>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // Parse and log the number of entries
            List<WorkLogEntry> workLogEntries = csvToBean.parse();
            logger.log(Level.INFO, "Successfully parsed {0} work log entries.", workLogEntries.size());

            // Display each work log entry
            for (WorkLogEntry entry : workLogEntries) {
                System.out.println(entry);
            }

            // Log successful completion
            logger.log(Level.INFO, "Finished displaying work log entries.");
        } catch (Exception e) {
            // Log the error with details
            logger.log(Level.SEVERE, "Error reading or parsing CSV file: " + e.getMessage(), e);
            e.printStackTrace(); // For debugging purposes
        }
    }
}