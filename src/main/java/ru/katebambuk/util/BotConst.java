package ru.katebambuk.util;

public enum BotConst {

    TOKEN("1019477278:AAGr5Hewtqt248NHnuUjS9L1HFN3Gjnzw6w"),
    USERNAME("BambukTestBot");

    private String value;

    BotConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
