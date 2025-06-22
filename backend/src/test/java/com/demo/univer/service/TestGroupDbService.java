package com.demo.univer.service;

import com.demo.univer.db.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class TestGroupDbService {
    private final GroupRepository groupRepository;
    private final TestStudentDbService testStudentDbService;

    public void deleteAllGroups() {
        testStudentDbService.deleteAllStudents();
        groupRepository.deleteAll();
    }
}
