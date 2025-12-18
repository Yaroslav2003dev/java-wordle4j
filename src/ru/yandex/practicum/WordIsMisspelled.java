package ru.yandex.practicum;

public class WordIsMisspelled extends RuntimeException {
    public WordIsMisspelled(String message) {
        super(message);
    }

}
