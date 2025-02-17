package lab1;

import java.io.*;

public class StreamParser implements AutoCloseable {
    private final Reader reader;
    private final StringBuilder wordBuilder = new StringBuilder();

    public StreamParser(InputStream inputStream) {
        this.reader = new InputStreamReader(inputStream);
    }

    public String nextWord() throws IOException {
        int c;
        while ((c = reader.read()) != -1) {
            char symbol = (char) c;
            if (Character.isLetterOrDigit(symbol)) {
                wordBuilder.append(symbol);
            } else if (wordBuilder.length() > 0) {
                String word = wordBuilder.toString().toLowerCase();
                wordBuilder.setLength(0);
                return word;
            }
        }

        if (wordBuilder.length() > 0) {
            String word = wordBuilder.toString().toLowerCase();
            wordBuilder.setLength(0);
            return word;
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
