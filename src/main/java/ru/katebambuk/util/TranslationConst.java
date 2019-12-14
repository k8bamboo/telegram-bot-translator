package ru.katebambuk.util;

public enum TranslationConst {
    LANG_EN_RU("lang", "en-ru"),
    LANG_RU_EN("lang", "ru-en"),
    ;

    private String key;
    private String value;

    TranslationConst(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
