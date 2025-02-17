package lab1;

import java.util.*;

public class WordStat {
    private final Map<String, Count> wordMap = new HashMap<>();

    public void addWord(String word) {
        wordMap.putIfAbsent(word, new Count());
        wordMap.get(word).increment();
    }

    public Map<String, Count> getStatistics() {
        return wordMap;
    }
}
