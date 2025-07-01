package com.demo.univer.step;

import com.demo.univer.error.ErrorType;
import com.demo.univer.grpc.group.v1.Group;
import com.demo.univer.grpc.student.v1.CreateStudentRequest;
import com.demo.univer.grpc.student.v1.CreateStudentResponse;
import com.demo.univer.grpc.student.v1.DeleteStudentRequest;
import com.demo.univer.grpc.student.v1.DeleteStudentResponse;
import com.demo.univer.grpc.student.v1.GetStudentsRequest;
import com.demo.univer.grpc.student.v1.GetStudentsResponse;
import com.demo.univer.grpc.student.v1.Student;
import com.demo.univer.grpc.student.v1.StudentServiceGrpc;
import com.demo.univer.utils.ErrorUtils;
import com.demo.univer.utils.TimeUtils;
import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RequiredArgsConstructor
public class StudentSteps {
    private final StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub;
    private final TimeUtils timeUtils;
    private final ErrorUtils errorUtils;

    public void checkEmptyList(Group group) {
        checkList(group, List.of());
    }

    public void checkList(Group group, List<Student> students) {
        GetStudentsRequest request = GetStudentsRequest.newBuilder()
                .setGroupId(group.getId())
                .build();
        GetStudentsResponse response = studentServiceBlockingStub.getStudents(request);

        Group responseGroup = response.getGroup();
        Assertions.assertEquals(group, responseGroup);

        List<Student> responseStudents = response.getStudentList();
        Assertions.assertEquals(students.size(), responseStudents.size());

        Map<Long, Student> searchMap = students.stream()
                .collect(Collectors.toMap(
                        Student::getId,
                        student -> student
                ));
        Assertions.assertEquals(students.size(), searchMap.size());

        for (Student responseStudent : responseStudents) {
            Student student = searchMap.get(responseStudent.getId());
            Assertions.assertNotNull(student);

            Assertions.assertEquals(responseStudent.getGroupId(), student.getGroupId());
            Assertions.assertEquals(responseStudent.getFio(), student.getFio());
            Assertions.assertEquals(responseStudent.getJoinDate(), student.getJoinDate());
        }
    }

    public void checkListError(Group group, ErrorType errorType) {
        GetStudentsRequest request = GetStudentsRequest.newBuilder()
                .setGroupId(group.getId())
                .build();

        errorUtils.assertError(errorType, () -> studentServiceBlockingStub.getStudents(request));
    }

    public Student createStudent(Group group, String fio, LocalDate joinDate) {
        Timestamp joinDateTimestamp = timeUtils.toTimestamp(joinDate);
        CreateStudentRequest request = CreateStudentRequest.newBuilder()
                .setGroupId(group.getId())
                .setFio(fio)
                .setJoinDate(joinDateTimestamp)
                .build();
        CreateStudentResponse response = studentServiceBlockingStub.createStudent(request);
        Student student = response.getStudent();

        Assertions.assertNotNull(student);
        Assertions.assertEquals(group.getId(), student.getGroupId());
        Assertions.assertEquals(fio, student.getFio());
        Assertions.assertEquals(joinDateTimestamp, student.getJoinDate());

        return student;
    }

    public void createStudentError(Group group, String fio, LocalDate joinDate, ErrorType errorType) {
        Timestamp joinDateTimestamp = timeUtils.toTimestamp(joinDate);
        CreateStudentRequest request = CreateStudentRequest.newBuilder()
                .setGroupId(group.getId())
                .setFio(fio)
                .setJoinDate(joinDateTimestamp)
                .build();

        errorUtils.assertError(errorType, () -> studentServiceBlockingStub.createStudent(request));
    }

    public void deleteStudent(Student student) {
        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentId(student.getId())
                .build();

        DeleteStudentResponse response = studentServiceBlockingStub.deleteStudent(request);
        Assertions.assertNotNull(response);
    }

    public void deleteStudentError(Student student, ErrorType errorType) {
        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentId(student.getId())
                .build();

        errorUtils.assertError(errorType, () -> studentServiceBlockingStub.deleteStudent(request));
    }
}
