package com.example.project_for_doctors.service;

import com.example.project_for_doctors.dto.DoctorDto;
import com.example.project_for_doctors.entity.Doctor;
import com.example.project_for_doctors.enums.DoctorState;
import com.example.project_for_doctors.model.ApiResponse;
import com.example.project_for_doctors.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public void saveChatId( String chatId) {
        doctorRepository.saveChatId(Long.valueOf(chatId));
    }

    public Optional<Doctor> findByChatId(Long chatId) {
        return doctorRepository.findByChatId(chatId);
    }

    public ResponseEntity<ApiResponse> updateState(Long chatId, DoctorState doctorState) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByChatId(chatId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setState(doctorState);
            doctorRepository.save(doctor);
            return ResponseEntity.ok().body(new ApiResponse(200, "Success", "state: " + doctor.getState()));
        }
        return ResponseEntity.notFound().build();
    }

   /* public ResponseEntity<ApiResponse> update(Long chatId, DoctorState state, DoctorDto doctorDto){
        Patient patient=patientRepository.findByPhone(phone);

        if (patient == null){
            return ResponseEntity.notFound().build();
        }else {

            Photo photo = photoService.findByUrl(patientDto.getPhotoUrl());
            patient.setFirstname(patientDto.getFirstname());
            patient.setLastname(patientDto.getLastname());
            patient.setPhoto(photo);
            patient.setAddress(patientDto.getAddress());

            saveToDb(patient);
        }
        return ResponseEntity.ok().body(new ApiResponse(200, "Doctor Updated", null));

    }*/
}
