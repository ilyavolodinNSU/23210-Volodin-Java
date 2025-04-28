package lab1;

import java.io.*;

public class Controller {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ошибка, передайте входной файл");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = "output.csv";

        try (StreamParser parser = new StreamParser(new FileInputStream(inputFilePath))) {
            WordStat wordStat = new WordStat();

            String word;
            while ((word = parser.nextWord()) != null) wordStat.addWord(word);

            ReportGenerator generator = new ReportGenerator();
            generator.generate(wordStat.getStatistics(), outputFilePath);

            System.out.println("Отчет сгенерирован: " + outputFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}