package com.demo.univer.test;

import com.demo.univer.config.StepConfiguration;
import com.demo.univer.config.TestcontainersConfiguration;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.gprc.student.Student;
import com.demo.univer.service.TestGroupDbService;
import com.demo.univer.step.GroupSteps;
import com.demo.univer.step.StudentSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(properties = {
        "spring.grpc.server.port=9092"}) //TODO This is working. But it is not good.
@Import({TestcontainersConfiguration.class, StepConfiguration.class})
class StudentTests {

    @Autowired
    private GroupSteps groupSteps;
    @Autowired
    private StudentSteps studentSteps;
    @Autowired
    private TestGroupDbService testGroupDbService;

    @BeforeEach
    void beforeEach() {
        testGroupDbService.deleteAllGroups();
    }

    @Test
    void checkEmptyStudentList() {
        Group group = groupSteps.createGroup("TEST");
        studentSteps.checkEmptyList(group);
    }

    @Test
    void createStudent() {
        Group group = groupSteps.createGroup("TEST");
        Student student = studentSteps.createStudent(
                group,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        studentSteps.checkList(group, List.of(student));
        groupSteps.checkList(List.of(group), List.of(1));
    }

    @Test
    void createTwoStudents() {
        Group group = groupSteps.createGroup("TEST");
        Student student1 = studentSteps.createStudent(
                group,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        Student student2 = studentSteps.createStudent(
                group,
                "Петров Петр Петрович",
                LocalDate.of(2024, 8, 21));
        studentSteps.checkList(group, List.of(student1, student2));
        groupSteps.checkList(List.of(group), List.of(2));
    }
}
