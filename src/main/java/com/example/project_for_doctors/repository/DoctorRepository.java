package com.example.project_for_doctors.repository;

import com.example.project_for_doctors.entity.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByChatId(Long chatId);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = " INSERT INTO doctor ( chat_id) VALUES (?)")
    void saveChatId( Long chatId);
}
