package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    public int getSteps() {
        return steps;
    }

    Random rand = new Random();

    private final WordleDictionary dictionary;
    private PrintWriter errorLog;
    private HashSet<String> useWords = new HashSet<>();
    private Map<Integer, Character> solution = new HashMap<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter errorLog) {
        this.errorLog=errorLog;
        this.dictionary=dictionary;
        int choice=rand.nextInt(dictionary.getWords().size());
        answer=dictionary.getWord(choice);
    }


    public String letterComparison(String str) {
        steps++;
        useWords.add(str);
        String timeStr = "";
        HashMap<Character, Integer> book=new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            book.put(answer.charAt(i),book.getOrDefault(answer.charAt(i),0)+1);
        }

        for (int i = 0; i < str.length(); i++) {
            if (answer.charAt(i) == str.charAt(i)) {
                timeStr+=("+");
                book.put(str.charAt(i),book.get(str.charAt(i))-1);
                if (solution.get(i) == null) {
                    solution.put(i, str.charAt(i));
                }
            } else {
                if (book.getOrDefault(str.charAt(i),0)>0) {
                    timeStr+=("^");
                    book.put(str.charAt(i),book.get(str.charAt(i))-1);
                } else {
                    timeStr+=("-");
                }
            }
        }
        return timeStr.toString();
    }
    public String getAnswer() {
        return answer;
    }

    public String searchWord() throws IOException {
        errorLog.println("Пользователь запросил подсказку");
        List<String> allWords = dictionary.getWords();
        for (String word : allWords) {

            if (useWords.contains(word)) {
                continue;
            }
            boolean matches = true;
            for (Map.Entry<Integer, Character> e : solution.entrySet()) {
                int pos = e.getKey();
                char expected = e.getValue();
                if (word.charAt(pos) != expected) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                useWords.add(word);
                errorLog.println("Подсказка: " + word);
                return word;
            }
        }
        errorLog.println("Слов в словаре больше нет");
        return "Слов в словаре больше нет";
    }
}

