package ru.katebambuk;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.katebambuk.bot.BambukTestBot;

public class BotStarter {

    private static Log4jLoggerAdapter log = (Log4jLoggerAdapter) LoggerFactory.getLogger(BotStarter.class);

    public static void main(String[] args) {
        initSystemProperties();

        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new BambukTestBot());
            log.info("Bot registered success");
        } catch (TelegramApiException e) {
            log.error("Register bot failed: ", e);
        }
    }

    private static void initSystemProperties() {
        PropertyConfigurator.configure(BotStarter.class.getClassLoader().getResource("log4j.properties"));
    }

}