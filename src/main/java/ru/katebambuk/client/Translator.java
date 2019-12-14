package ru.katebambuk.client;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import ru.katebambuk.client.dto.YandexTranslateResponseDto;
import ru.katebambuk.util.TranslationConst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.NoSuchElementException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.*;
import static ru.katebambuk.util.HttpConst.YA_TRANSLATE_KEY;

public class Translator {

    private static final String YA_TRANSLATE_API_URI = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final Gson gsonHelper = new Gson();

    public String translate(TranslationConst translateDirection, String sourceText) {
        if (isNull(sourceText) || sourceText.isEmpty()) {
            return "";
        }

        String requestParametrizedUrl = getRequestParametrizedUrl(requireNonNull(translateDirection), sourceText);
        HttpResponse response = createHttpRequest(requestParametrizedUrl);
        String responseJson = readResponseText(requireNonNull(response).getEntity());
        return getTranslatedTextFromResponseJson(responseJson);
    }

    private String getRequestParametrizedUrl(TranslationConst lang, String text) {
        StringBuilder url = new StringBuilder();
        try {
            url = new StringBuilder(YA_TRANSLATE_API_URI)
                    .append("?").append(YA_TRANSLATE_KEY.getKey()).append("=").append(YA_TRANSLATE_KEY.getValue())
                    .append("&").append(lang.getKey()).append("=").append(lang.getValue())
                    .append("&").append("text").append("=").append((URLEncoder.encode(text, UTF_8.name())));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url.toString();
    }

    private HttpResponse createHttpRequest(String url) {

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            return httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String readResponseText(HttpEntity entity) {
        StringBuilder translatedText = new StringBuilder();
        if (nonNull(entity)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                while (reader.ready()) {
                    translatedText.append(reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return translatedText.toString();
    }

    private String getTranslatedTextFromResponseJson(String responseJson) {
        String[] text = gsonHelper.fromJson(responseJson, YandexTranslateResponseDto.class).getText();
        if (text.length > 0) {
            return text[0];
        } else {
            throw new NoSuchElementException("There is no translated text in response json");
        }
    }

}
