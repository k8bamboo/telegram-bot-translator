package ru.katebambuk.client.dto;

import java.io.Serializable;

public class YandexTranslateResponseDto implements Serializable {

    private String code;
    private String lang;
    private String[] text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
