package com.demo.univer.db.service;

import com.demo.univer.db.entity.StudentEntity;

import java.time.LocalDate;
import java.util.List;

public interface StudentDbService {
    List<StudentEntity> getStudents(long groupId);

    StudentEntity createStudent(long groupId, String fio, LocalDate joinDate);
}
