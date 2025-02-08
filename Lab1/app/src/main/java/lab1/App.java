package lab1;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        if (args.length == 0) {
            System.err.println("Usage: java WordFrequencyCounter <input file>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = "output.csv";

        Map<String, Integer> wordFrequencyMap = parseToHashMap(inputFilePath);
        if (wordFrequencyMap == null) return;

        mapToCSV(wordFrequencyMap, outputFilePath);
    }

    public static Map<String, Integer> parseToHashMap(String filePath) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        
        try (Reader reader = new InputStreamReader(new FileInputStream(filePath))) {
            StringBuilder wordBuilder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                char symbol = (char)c;
                if (Character.isLetterOrDigit(symbol)) {
                    wordBuilder.append(symbol);
                } else if (wordBuilder.length() > 0) {
                    String word = wordBuilder.toString().toLowerCase();
                    wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                    wordBuilder.setLength(0);
                }
            }
            // Последнее слово
            if (wordBuilder.length() > 0) {
                String word = wordBuilder.toString().toLowerCase();
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                wordBuilder.setLength(0);
            }

        } catch (Exception e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
            return null;
        }
        
        return wordFrequencyMap;
    }

    public static void mapToCSV(Map<String, Integer> wordFrequencyMap, String outputFilePath) {
        // Сортировка по убыванию частоты
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequencyMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Подсчет общего количества слов
        int totalWords = wordFrequencyMap.values().stream().mapToInt(Integer::intValue).sum();

        // Пишем в CSV
        try (Writer writer = new FileWriter(outputFilePath)) {
            writer.write("Word,Frequency,Frequency (%)\n");
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String word = entry.getKey();
                int frequency = entry.getValue();
                double frequencyPercent = (frequency * 100.0) / totalWords;
                writer.write(String.format("%s,%d,%.5f%%\n", word, frequency, frequencyPercent));
            }
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getLocalizedMessage());
        }
    }
}
