package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    static final String HOME = System.getProperty("user.home");

    public static void main(String[] args) {
        try (PrintWriter errorLog = new PrintWriter(new FileWriter(Paths.get(HOME, "error.log").toFile()))) {
            errorLog.flush();
            WordleDictionaryLoader text = new WordleDictionaryLoader(errorLog);
            WordleDictionary listWords = text.loadText();
            WordleGame wordleGame = new WordleGame(listWords, errorLog);
            Scanner input = new Scanner(System.in);
            String word = "";
            String output;
            String help;
            errorLog.println("Игра началась!");
            errorLog.println("Загадано слово: " + wordleGame.getAnswer());
            errorLog.flush();
            boolean isNotCorrectInput;
            while (wordleGame.getSteps() < 6) {
                isNotCorrectInput = TRUE;
                while (isNotCorrectInput) {
                    System.out.println("Введите слово");
                    word = input.nextLine().replace('ё', 'е').toLowerCase();
                    errorLog.println("Пользователь ввёл: " + word);
                    errorLog.flush();

                    if (!word.isEmpty() && word.trim().isEmpty()) {
                        System.out.println("Вы ввели пробел. В слове этот символ должен отсутствовать");
                        continue;
                    }

                    if (!word.equals("") && !word.matches("^[А-Яа-я\\s]+$")) {
                        System.out.println("Ошибка ввода. Введите слово, используя только русские буквы");
                        errorLog.println("Ошибка ввода. Введите слово, используя только русские буквы");
                        errorLog.flush();
                        continue;
                    }
                    if (!word.equals("") && !listWords.isWordFromListWords(word)) {
                        System.out.println("Ошибка ввода. Введённого слова нет в словаре");
                        errorLog.println("Ошибка ввода. Введённого слова нет в словаре");
                        errorLog.flush();
                        continue;
                    }
                    if ((word.length() == 5) || word.equals("")) {
                        isNotCorrectInput = FALSE;
                    } else {
                        System.out.println("Ошибка ввода. Длина слова должна быть 5 символов. Вы ввели слово с длиной - " + word.length());
                        errorLog.println("Ошибка ввода. Длина слова должна быть 5 символов. Вы ввели слово с длиной - " + word.length());
                        errorLog.flush();
                    }

                }

                if (word.equals("")) {
                    help = wordleGame.searchWord();
                    System.out.println("Подсказка от компьютера: " + help);
                } else {
                    output = wordleGame.letterComparison(word);
                    if (output.equals("+++++")) {
                        System.out.println("Слово отгадано!");
                        System.out.println("Игра завершена победой :)");
                        errorLog.println("Слово отгадано!");
                        errorLog.println("Игра завершена победой");
                        errorLog.flush();
                        return;
                    } else {
                        System.out.println(output);
                        errorLog.println("Вывод: " + output);
                        errorLog.flush();
                    }
                }
            }
            System.out.println("Вы не смогли отгадать слово - " + wordleGame.getAnswer());
            System.out.println("Игра завершена поражением :(");
            errorLog.println("Игра завершена поражением");
            errorLog.flush();
        } catch (IOException ex) {
            System.out.println("Игра завершилась ошибкой");
            ex.printStackTrace();
        }

    }
}
