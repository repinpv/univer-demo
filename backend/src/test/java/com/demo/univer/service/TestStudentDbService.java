package com.demo.univer.service;

import com.demo.univer.db.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TestStudentDbService {
    private final StudentRepository studentRepository;

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}
