package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import static ru.yandex.practicum.Wordle.HOME;

class WordleTest {

    @Test
    @DisplayName("Получение результата сравнения введённого слова, отсутсвующего в словаре, с загаданным словом")
    void testLetterComparison_WhenWordIsMissingFromDictionary_False() throws IOException {
        //given
            PrintWriter errorLog = new PrintWriter(new FileWriter(Paths.get(HOME, "error.log").toFile()));
            WordleDictionaryLoader text = new WordleDictionaryLoader(errorLog);
            WordleDictionary listWords = text.loadText();
            WordleGame wordleGame = new WordleGame(listWords, errorLog);
            String word = "тигры";
            String output;
            //when
            output = wordleGame.letterComparison(word);
            //then
            Assertions.assertFalse(output.equals("+++++"),"Слова нет в словаре, поэтому ожидается False");
        }

    @Test
    @DisplayName("Получение результата сравнения загаданного слова с загаданным словом")
    void testLetterComparison_WordIsAnswer_True() throws IOException {
        //given
        PrintWriter errorLog = new PrintWriter(new FileWriter(Paths.get(HOME, "error.log").toFile()));
        WordleDictionaryLoader text = new WordleDictionaryLoader(errorLog);
        WordleDictionary listWords = text.loadText();
        WordleGame wordleGame = new WordleGame(listWords, errorLog);
        String word = wordleGame.getAnswer();
        String output;
        //when
        output = wordleGame.letterComparison(word);
        //then
        Assertions.assertTrue(output.equals("+++++"),"Загаданное слово сравнивается с самим собой, поэтому ожидается True");
    }

        @Test
        @DisplayName("Отгадывание слова за счёт подсказки")
        void testSearchWord_DeterminingFinalLetterWordUsingHelp_True () throws IOException {
            //given
            PrintWriter errorLog = new PrintWriter(new FileWriter(Paths.get(HOME, "error.log").toFile()));
            WordleDictionaryLoader text = new WordleDictionaryLoader(errorLog);
            WordleDictionary listWords = text.loadText();
            WordleGame wordleGame = new WordleGame(listWords, errorLog);
            StringBuilder word1 = new StringBuilder(wordleGame.getAnswer());
            String word2= String.valueOf(word1.delete(3,5));
            String help="";
            //when
            wordleGame.letterComparison(word2);
            while (!help.equals(wordleGame.getAnswer())){
                help = wordleGame.searchWord();
            }
            //then
            Assertions.assertTrue(help.equals(wordleGame.getAnswer()),"По подсказке получится отгадать слово, ожидается True");
        }
    }
