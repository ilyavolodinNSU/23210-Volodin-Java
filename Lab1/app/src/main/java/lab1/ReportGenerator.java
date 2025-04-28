package lab1;

import java.io.*;
import java.util.*;

public class ReportGenerator {
    public void generate(Map<String, Count> wordStatistics, String outputFilePath) {
        List<Map.Entry<String, Count>> sortedEntries = new ArrayList<>(wordStatistics.entrySet());
        sortedEntries.sort((e1, e2) -> Integer.compare(e2.getValue().getCount(), e1.getValue().getCount()));

        int totalWords = sortedEntries.stream().mapToInt(e -> e.getValue().getCount()).sum();

        try (Writer writer = new FileWriter(outputFilePath)) {
            writer.write("Word,Frequency,Frequency (%)\n");
            for (Map.Entry<String, Count> entry : sortedEntries) {
                String word = entry.getKey();
                int frequency = entry.getValue().getCount();
                double frequencyPercent = (frequency * 100.0) / totalWords;
                writer.write(String.format("%s,%d,%.5f%%\n", word, frequency, frequencyPercent));
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
