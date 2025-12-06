package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;
    private PrintWriter errorLog;

    public WordleDictionary(List<String> words, PrintWriter errorLog) throws IOException {
        this.errorLog = errorLog;
        this.words = words;
        errorLog.println("Загружен словарь");
        errorLog.flush();
        normalizationDictionary();
    }

    public List<String> getWords() {
        return words;
    }

    public String getWord(int i) {
        return words.get(i);
    }

    public boolean isWordFromListWords(String word) {
        return words.contains(word);
    }

    public void normalizationDictionary() throws IOException {
        List<String> normWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == 5) {
                word.replace('ё', 'е');
                word.toLowerCase();
                normWords.add(word);
            }
        }
        words = normWords;
        errorLog.println("Произведена нормализация словаря");
        errorLog.flush();
    }


}
