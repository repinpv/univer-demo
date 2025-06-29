package com.demo.univer.step;

import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import com.demo.univer.grpc.group.v1.Group;
import com.demo.univer.grpc.student.v1.CreateStudentRequest;
import com.demo.univer.grpc.student.v1.CreateStudentResponse;
import com.demo.univer.grpc.student.v1.GetStudentsRequest;
import com.demo.univer.grpc.student.v1.GetStudentsResponse;
import com.demo.univer.grpc.student.v1.Student;
import com.demo.univer.grpc.student.v1.StudentServiceGrpc;
import com.demo.univer.utils.TimeUtils;
import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StudentSteps {
    private final StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub;
    private final TimeUtils timeUtils;
    private final ErrorFactory errorFactory;

    public void checkEmptyList(Group group) {
        GetStudentsRequest getStudentsRequest = GetStudentsRequest.newBuilder()
                .setGroupId(group.getId())
                .build();
        GetStudentsResponse getStudentsResponse = studentServiceBlockingStub.getStudents(getStudentsRequest);

        Assertions.assertTrue(getStudentsResponse.getStudentList().isEmpty());
    }

    public Student createStudent(Group group, String fio, LocalDate joinDate) {
        Timestamp joinDateTimestamp = timeUtils.toTimestamp(joinDate);
        CreateStudentRequest createStudentRequest = CreateStudentRequest.newBuilder()
                .setGroupId(group.getId())
                .setFio(fio)
                .setJoinDate(joinDateTimestamp)
                .build();
        CreateStudentResponse createStudentResponse = studentServiceBlockingStub.createStudent(createStudentRequest);
        Student student = createStudentResponse.getStudent();

        Assertions.assertNotNull(student);
        Assertions.assertEquals(group.getId(), student.getGroupId());
        Assertions.assertEquals(fio, student.getFio());
        Assertions.assertEquals(joinDateTimestamp, student.getJoinDate());

        return student;
    }

    public void createStudentError(Group group, String fio, LocalDate joinDate, ErrorType errorType) {
        Timestamp joinDateTimestamp = timeUtils.toTimestamp(joinDate);
        CreateStudentRequest createStudentRequest = CreateStudentRequest.newBuilder()
                .setGroupId(group.getId())
                .setFio(fio)
                .setJoinDate(joinDateTimestamp)
                .build();

        StatusRuntimeException statusRuntimeException = Assertions.assertThrows
                (StatusRuntimeException.class,
                        () -> studentServiceBlockingStub.createStudent(createStudentRequest));

        Assertions.assertEquals(errorType.getStatus().getCode(), statusRuntimeException.getStatus().getCode());
        Assertions.assertEquals(errorType.getErrorCode(), errorFactory.getErrorCode(statusRuntimeException));
    }

    public void checkList(Group group, List<Student> students) {
        GetStudentsRequest getStudentsRequest = GetStudentsRequest.newBuilder()
                .setGroupId(group.getId())
                .build();
        GetStudentsResponse getStudentsResponse = studentServiceBlockingStub.getStudents(getStudentsRequest);
        List<Student> responseStudents = getStudentsResponse.getStudentList();
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
        GetStudentsRequest getStudentsRequest = GetStudentsRequest.newBuilder()
                .setGroupId(group.getId())
                .build();

        StatusRuntimeException statusRuntimeException = Assertions.assertThrows
                (StatusRuntimeException.class,
                        () -> studentServiceBlockingStub.getStudents(getStudentsRequest));

        Assertions.assertEquals(errorType.getStatus().getCode(), statusRuntimeException.getStatus().getCode());
        Assertions.assertEquals(errorType.getErrorCode(), errorFactory.getErrorCode(statusRuntimeException));
    }
}
