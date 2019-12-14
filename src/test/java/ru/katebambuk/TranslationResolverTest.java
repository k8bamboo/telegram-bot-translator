package ru.katebambuk;

import org.junit.Test;
import ru.katebambuk.client.Translator;

import static org.junit.Assert.assertEquals;
import static ru.katebambuk.util.TranslationConst.LANG_EN_RU;
import static ru.katebambuk.util.TranslationConst.LANG_RU_EN;

public class TranslationResolverTest {
    private Translator translator = new Translator();

    @Test
    public void shouldCorrectTranslateFromRuToEn() {
        String result = translator.translate(LANG_RU_EN, "привет");
        assertEquals("hi", result);
    }

    @Test
    public void shouldCorrectTranslateFromEnToRu() {
        String result = translator.translate(LANG_EN_RU, "hi");
        assertEquals("привет", result);
    }

    @Test
    public void shouldCorrectTranslateNull() {
        String result = translator.translate(LANG_EN_RU, null);
        assertEquals("", result);
    }

    @Test
    public void shouldCorrectTranslateEmptyString() {
        String result = translator.translate(LANG_EN_RU, "");
        assertEquals("", result);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowsExceptionIfTranslateDirectionIsNull() {
        translator.translate(null, "123");
    }


}
