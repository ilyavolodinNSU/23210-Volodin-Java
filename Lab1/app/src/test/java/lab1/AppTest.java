package lab1;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void testStreamParser() throws IOException {
        String text = "Hello, world! 123 Java.";
        InputStream inputStream = new ByteArrayInputStream(text.getBytes());
        StreamParser parser = new StreamParser(inputStream);

        assertEquals("hello", parser.nextWord());
        assertEquals("world", parser.nextWord());
        assertEquals("123", parser.nextWord());
        assertEquals("java", parser.nextWord());
        assertNull(parser.nextWord());  

        parser.close();
    }

    @Test
    void testWordStat() {
        WordStat stat = new WordStat();
        stat.addWord("java");
        stat.addWord("java");
        stat.addWord("python");

        Map<String, Count> stats = stat.getStatistics();
        assertEquals(2, stats.get("java").getCount());
        assertEquals(1, stats.get("python").getCount());
    }

    @Test
    void testCount() {
        Count count = new Count();
        assertEquals(0, count.getCount());

        count.increment();
        assertEquals(1, count.getCount());

        count.increment();
        assertEquals(2, count.getCount());
    }

    @Test
    void testReportGenerator() throws IOException {
        Map<String, Count> testData = new HashMap<>();
        Count countJava = new Count();
        countJava.increment();
        countJava.increment();
        testData.put("java", countJava);

        Count countPython = new Count();
        countPython.increment();
        testData.put("python", countPython);

        File tempFile = File.createTempFile("test_report", ".csv");
        tempFile.deleteOnExit();

        ReportGenerator generator = new ReportGenerator();
        generator.generate(testData, tempFile.getAbsolutePath());

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        assertEquals("Word,Frequency,Frequency (%)", lines.get(0));
        assertTrue(lines.stream().anyMatch(l -> l.startsWith("java,2")));
        assertTrue(lines.stream().anyMatch(l -> l.startsWith("python,1")));
    }
}
