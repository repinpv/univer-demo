package com.demo.univer.test;

import com.demo.univer.config.StepConfiguration;
import com.demo.univer.config.TestcontainersConfiguration;
import com.demo.univer.error.ErrorType;
import com.demo.univer.grpc.group.v1.Group;
import com.demo.univer.grpc.student.v1.Student;
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

@SpringBootTest
//@DirtiesContext // Ensure a clean context for each test
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

    @Test
    void groupNotFound() {
        Group group = Group.newBuilder()
                .setId(0L)
                .build();

        studentSteps.checkListError(group, ErrorType.GROUP_NOT_FOUND);

        LocalDate joinDate = LocalDate.of(2024, 9, 1);
        studentSteps.createStudentError(group, "Иванов Иван Иванович", joinDate, ErrorType.GROUP_NOT_FOUND);
    }

    @Test
    void goodFio() {
        LocalDate joinDate = LocalDate.of(2024, 9, 1);
        Group group = groupSteps.createGroup("TEST");

        studentSteps.createStudent(group, "St Ав Tt", joinDate);
        studentSteps.createStudent(group, "Иванов Иван Иванович", joinDate);
        studentSteps.createStudent(group, "Иванов Иван", joinDate);
    }

    @Test
    void invalidFio() {
        LocalDate joinDate = LocalDate.of(2024, 9, 1);
        Group group = groupSteps.createGroup("TEST");

        studentSteps.createStudentError(
                group, "A", joinDate, ErrorType.STUDENT_FIO_FORMAT_INVALID);
        studentSteps.createStudentError(
                group, "ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345A", joinDate, ErrorType.STUDENT_FIO_FORMAT_INVALID);
        studentSteps.createStudentError(
                group, "Иванов", joinDate, ErrorType.STUDENT_FIO_FORMAT_INVALID);
        studentSteps.createStudentError(
                group, "Иванов Иван И", joinDate, ErrorType.STUDENT_FIO_FORMAT_INVALID);
        studentSteps.createStudentError(
                group, "Иванов Иван И.", joinDate, ErrorType.STUDENT_FIO_FORMAT_INVALID);
    }

    @Test
    void deleteStudent() {
        Group group = groupSteps.createGroup("TEST");
        Student student = studentSteps.createStudent(
                group,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        studentSteps.checkList(group, List.of(student));
        groupSteps.checkList(List.of(group), List.of(1));

        studentSteps.deleteStudent(student);
        studentSteps.checkEmptyList(group);
        groupSteps.checkList(List.of(group), List.of(0));
    }

    @Test
    void createTwoStudentsAndDeleteOne() {
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

        studentSteps.deleteStudent(student1);
        studentSteps.checkList(group, List.of(student2));
        groupSteps.checkList(List.of(group), List.of(1));
    }

    @Test
    void createTwoStudentsInDifferentGroupsAndDeleteOne() {
        Group group1 = groupSteps.createGroup("TEST1");
        Group group2 = groupSteps.createGroup("TEST2");
        groupSteps.checkList(List.of(group1, group2), List.of(0, 0));

        Student student1 = studentSteps.createStudent(
                group1,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        Student student2 = studentSteps.createStudent(
                group2,
                "Петров Петр Петрович",
                LocalDate.of(2024, 8, 21));
        studentSteps.checkList(group1, List.of(student1));
        studentSteps.checkList(group2, List.of(student2));
        groupSteps.checkList(List.of(group1, group2), List.of(1, 1));

        studentSteps.deleteStudent(student1);
        studentSteps.checkList(group1, List.of());
        studentSteps.checkList(group2, List.of(student2));
        groupSteps.checkList(List.of(group1, group2), List.of(0, 1));
    }

    @Test
    void deleteNotExistStudent() {
        Student student = Student.newBuilder()
                .setId(1)
                .build();
        studentSteps.deleteStudentError(student, ErrorType.STUDENT_NOT_FOUND);
    }

    @Test
    void deleteStudentTwice() {
        Group group = groupSteps.createGroup("TEST");
        Student student = studentSteps.createStudent(
                group,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        studentSteps.checkList(group, List.of(student));
        groupSteps.checkList(List.of(group), List.of(1));

        studentSteps.deleteStudent(student);
        studentSteps.checkEmptyList(group);
        groupSteps.checkList(List.of(group), List.of(0));

        studentSteps.deleteStudentError(student, ErrorType.STUDENT_NOT_FOUND);
    }

    @Test
    void exceedGroupMaxMemberNumber() {
        Group group = groupSteps.createGroup("TEST", 1);
        Student student1 = studentSteps.createStudent(
                group,
                "Иванов Иван Иванович",
                LocalDate.of(2024, 9, 1));
        studentSteps.checkList(group, List.of(student1));
        groupSteps.checkList(List.of(group), List.of(1));

        studentSteps.createStudentError(
                group,
                "Петров Петр Петрович",
                LocalDate.of(2024, 8, 21),
                ErrorType.GROUP_MEMBER_NUMBER_EXCEEDED);
    }
}
