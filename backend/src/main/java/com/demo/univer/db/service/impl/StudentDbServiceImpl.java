package com.demo.univer.db.service.impl;

import com.demo.univer.db.entity.StudentEntity;
import com.demo.univer.db.repository.StudentRepository;
import com.demo.univer.db.service.GroupDbService;
import com.demo.univer.db.service.StudentDbService;
import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentDbServiceImpl implements StudentDbService {
    private final StudentRepository studentRepository;
    private final GroupDbService groupDbService;
    private final ErrorFactory errorFactory;

    @Override
    public List<StudentEntity> getStudents(long groupId) {
        return studentRepository.findAllByGroupId(groupId);
    }

    @Override
    public StudentEntity createStudent(long groupId, String fio, LocalDate joinDate) {
        groupDbService.checkGroupExists(groupId);

        StudentEntity studentEntity = StudentEntity.builder()
                .groupId(groupId)
                .fio(fio)
                .joinDate(joinDate)
                .build();

        studentRepository.save(studentEntity);
        studentEntity.setId(studentRepository.getLastId());

        return studentEntity;
    }

    @Override
    public void deleteStudent(long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> errorFactory.create(ErrorType.STUDENT_NOT_FOUND));

        studentRepository.delete(studentEntity);
    }
}
