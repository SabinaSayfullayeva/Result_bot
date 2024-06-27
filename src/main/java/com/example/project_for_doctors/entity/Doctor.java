package com.example.project_for_doctors.entity;

import com.example.project_for_doctors.enums.DoctorState;
import com.example.project_for_doctors.enums.DoctorStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    DoctorState state;
    @Enumerated(EnumType.STRING)
    DoctorStatus status;
    Long chatId;
    String firstname;
    String lastname;
    String speciality;
}
