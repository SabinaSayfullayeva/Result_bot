package com.example.project_for_doctors;

import com.example.project_for_doctors.bot.DoctorsBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class ProjectForDoctorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectForDoctorsApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(DoctorsBot doctorsBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(doctorsBot);
        return telegramBotsApi;
    }
}
