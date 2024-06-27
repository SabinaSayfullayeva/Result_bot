package com.example.project_for_doctors.bot;

import com.example.project_for_doctors.dto.DoctorDto;
import com.example.project_for_doctors.entity.Doctor;
import com.example.project_for_doctors.enums.DoctorState;
import com.example.project_for_doctors.service.DoctorService;
import com.example.project_for_doctors.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static com.example.project_for_doctors.enums.DoctorState.FIRSTNAME;
import static com.example.project_for_doctors.enums.DoctorState.LASTNAME;

@Component
@RequiredArgsConstructor

public class DoctorsBot extends TelegramLongPollingBot {
    private final BotService botService;
    private final DoctorService doctorService;
    private final ValidationService validationService;

    private HashMap<Long, DoctorState> doctorStateHashMap = new HashMap<>();
    private HashMap<Long, DoctorDto> doctorList = new HashMap<>();
    private DoctorState doctorState = null;
    @Override
    public String getBotUsername() {
        return "https://t.me/doctors_registration_bot";
    }

    @Override
    public String getBotToken() {
        return "7463024014:AAFx9-3UAhwHvUagLjGh26CxTV0euiDjnUc";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            Long chatId = message.getChatId();

            if (Objects.equals(text, "/start")) {

                doctorService.saveChatId(String.valueOf(chatId));
                doctorStateHashMap.put(chatId, DoctorState.START);
                doctorService.updateState(chatId, DoctorState.START);
            } else if (doctorStateHashMap.get(chatId) == null || doctorStateHashMap.get(chatId) == DoctorState.DEFAULT) {
                doctorStateHashMap.put(chatId, DoctorState.DEFAULT);
                execute(botService.sendStart(chatId.toString()));
            }

            Optional<Doctor> currentDoctor= doctorService.findByChatId(chatId);
            if (currentDoctor.isPresent()){

                doctorState = currentDoctor.get().getState();
                switch (doctorState){
                    case START -> {

                        execute(botService.enterFirstname(chatId.toString()));
                        doctorService.updateState(chatId, FIRSTNAME);
                        doctorStateHashMap.put(chatId, FIRSTNAME);
                    }

                case FIRSTNAME -> {
                    String validationMessage = validationService.validateNames(text, "Ism");
                    if (validationMessage == null) {
                        String firstName = text;
                        execute(botService.enterLastName(chatId.toString()));
                        doctorService.updateState(chatId, LASTNAME);
                        doctorStateHashMap.put(chatId, LASTNAME);
                        DoctorDto patientDTO = doctorList.get(chatId);
                        patientDTO.setFirstname(firstName);
                        doctorList.put(chatId, patientDTO);
                        System.out.println(patientDTO);
                    } else {
                        execute(botService.validationMessage(chatId.toString(), validationMessage));
                    }
                    }
                    case LASTNAME -> {
                        String validationMessage = validationService.validateNames(text, "Familya");
                        if (validationMessage == null) {
                            String lastName = text;
                            execute(botService.enterSpeciality(chatId.toString()));
                            doctorService.updateState(chatId, DoctorState.SPECIALITY);
                            doctorStateHashMap.put(chatId, DoctorState.SPECIALITY);
                            DoctorDto doctorDto = doctorList.get(chatId);
                            doctorDto.setLastname(lastName);
                            doctorList.put(chatId, doctorDto);
                        } else {
                            execute(botService.validationMessage(chatId.toString(), validationMessage));
                        }
                    }
                    case SPECIALITY -> {
                        String speciality = text;
                        DoctorDto doctorDto= doctorList.get(chatId);
                        doctorDto.setSpeciality(speciality);
                        doctorList.put(chatId, doctorDto);
                      //  doctorService.update( chatId, DoctorState.DOCTOR_IS_FREE, doctorDto);
                        doctorService.updateState(chatId, DoctorState.DOCTOR_IS_FREE);
                        doctorStateHashMap.put(chatId, DoctorState.DOCTOR_IS_FREE);
                       // execute(botService.registrationFinished(chatId.toString()));
                    }
                }
            }
        }
    }
}
