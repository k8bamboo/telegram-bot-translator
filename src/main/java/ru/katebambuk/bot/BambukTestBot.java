package ru.katebambuk.bot;

import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.katebambuk.client.Translator;
import ru.katebambuk.util.BotConst;
import ru.katebambuk.util.TranslationConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static ru.katebambuk.util.TranslationConst.LANG_EN_RU;
import static ru.katebambuk.util.TranslationConst.LANG_RU_EN;

public class BambukTestBot extends TelegramLongPollingBot {

    private static final Log4jLoggerAdapter log = (Log4jLoggerAdapter) LoggerFactory.getLogger(BambukTestBot.class);

    private static final Translator translator = new Translator();

    private Map<Long, TranslationConst> translationModeForChat = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message messageData = update.getMessage();
            Long chatId = messageData.getChatId();
            log.info("message from {}", messageData.getChat().getUserName());
            String text = messageData.getText();
            log.info("message text: {}", text);
            if (isNull(text)) {
                sendMessage(buildSendMessage(chatId, "Sorry, I don't understand you. Send me text, please :3"));
            } else if (text.equals("/start")) {
                String answerText = String.format("Hello, %s! I'm translator RU-EN and EN-RU! Please, choose source text lang",
                        messageData.getChat().getFirstName());

                sendMessage(buildSendMessageWithKeyboard(chatId, answerText));
            } else if (text.equals("/ru")) {
                setMode(chatId, LANG_RU_EN, "Отличный выбор! Давай переводить");
            } else if (text.equals("/en")) {
                setMode(chatId, LANG_EN_RU, "Good choice! Let's translate");
            } else {
                TranslationConst modeForThisChat = getModeForThisChat(chatId);
                if (isNull(modeForThisChat)) {
                    sendMessage(buildSendMessage(chatId, "Please, choose source text lang"));
                } else {
                    String translatedText = translator.translate(modeForThisChat, text);
                    sendMessage(buildSendMessage(chatId, translatedText));
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callback = callbackQuery.getData();
            long chatId = (long) update.getCallbackQuery().getFrom().getId();
            if (callback.startsWith("/ru")) {
                setMode(chatId, LANG_RU_EN, "Отличный выбор! Давай переводить");
            } else if (callback.startsWith("/en")) {
                setMode(chatId, LANG_EN_RU, "Good choice! Let's translate");
            }
        }
    }

    private void setMode(Long chatId, TranslationConst lang, String text) {
        setModeForThisChat(chatId, lang);
        sendMessage(buildSendMessage(chatId, text));
    }

    private void setModeForThisChat(Long chatId, TranslationConst lang) {
        translationModeForChat.put(chatId, lang);
    }

    private TranslationConst getModeForThisChat(Long chatId) {
        return translationModeForChat.get(chatId);
    }

    private SendMessage buildSendMessage(Long chatId, String text) {
        return new SendMessage().setChatId(chatId).setText(text);
    }

    private SendMessage buildSendMessageWithKeyboard(Long chatId, String text) {
        return buildSendMessage(chatId, text).setReplyMarkup(getKeyboardMarkup());
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup getKeyboardMarkup() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        buttonsRow.add(new InlineKeyboardButton().setText("RU-EN").setCallbackData("/ru"));
        buttonsRow.add(new InlineKeyboardButton().setText("EN-RU").setCallbackData("/en"));
        buttons.add(buttonsRow);

        return new InlineKeyboardMarkup().setKeyboard(buttons);
    }

    @Override
    public String getBotToken() {
        return BotConst.TOKEN.getValue();
    }

    @Override
    public String getBotUsername() {
        return BotConst.USERNAME.getValue();
    }
}
