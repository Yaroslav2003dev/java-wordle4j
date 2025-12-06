package ru.yandex.practicum;

public class WordDoesnNotFitTheLength extends RuntimeException {
    private final int sizeWord;

    public WordDoesnNotFitTheLength(String message, final int sizeWord) {
        super(message);
        this.sizeWord = sizeWord;
    }

    public String getDetailMessage() {
        return getMessage() + " " + sizeWord;
    }
}
