package ru.katebambuk.util;

public enum HttpConst {

    YA_TRANSLATE_KEY("key", "trnsl.1.1.20191208T114736Z.5dd2b778f13d58f1.6e7273c9a91fb0b7f6647fd1d419fbaa34666755"),
    ;


    private String key;
    private String value;

    HttpConst(String key, String value) {
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
