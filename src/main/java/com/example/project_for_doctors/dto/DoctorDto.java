package com.example.project_for_doctors.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDto {
    Long id;
    Long chatId;
    String firstname;
    String lastname;
    String speciality;
}
