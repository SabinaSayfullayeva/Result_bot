package com.example.project_for_doctors.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class BotService {
    public SendMessage sendStart(String chatId) {
        return new SendMessage(chatId, "Boshlash uchun /start buyrug'ini kiriting");
    }
    public SendMessage enterFirstname(String chatId) {
        return new SendMessage(chatId,"Ismingizni kiriting:");
    }
    public SendMessage enterLastName(String chatId) {
        return new SendMessage(chatId,"Familiyangizni kiriting:");
    }
    public SendMessage validationMessage(String chatId, String validationMessage) {
        return new SendMessage(chatId, validationMessage);
    }

    public SendMessage enterSpeciality(String chatId) {
        return new SendMessage(chatId,"Speciality kiriting:");
    }
}
