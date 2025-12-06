package ru.yandex.practicum;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private List<String> textList = new ArrayList<>();
    private PrintWriter errorLog;

    public WordleDictionaryLoader(PrintWriter errorLog) {
        this.errorLog = errorLog;
    }

    public WordleDictionary loadText() throws IOException {
        try (FileReader reader = new FileReader("words_ru.txt")) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                textList.add(br.readLine());
            }
            br.close();
        } catch (FileNotFoundException e) {
            errorLog.println("Файл не найден");
        } catch (IOException e) {
            errorLog.println("Произошла ошибка во время чтения файла");
        }

        return new WordleDictionary(textList, errorLog);
    }


}
