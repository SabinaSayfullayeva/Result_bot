package com.example.project_for_doctors.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class BotInitializer {
    private final DoctorsBot doctorsBot;
    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(doctorsBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
