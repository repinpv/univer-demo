package com.demo.univer.grpc;

import com.demo.univer.db.entity.StudentEntity;
import com.demo.univer.db.service.StudentDbService;
import com.demo.univer.gprc.student.CreateStudentRequest;
import com.demo.univer.gprc.student.CreateStudentResponse;
import com.demo.univer.gprc.student.GetStudentsRequest;
import com.demo.univer.gprc.student.GetStudentsResponse;
import com.demo.univer.gprc.student.Student;
import com.demo.univer.gprc.student.StudentServiceGrpc;
import com.demo.univer.mapper.StudentMapper;
import com.demo.univer.utils.TimeUtils;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class StudentGrpcService extends StudentServiceGrpc.StudentServiceImplBase {
    private final StudentDbService studentDbService;
    private final StudentMapper studentMapper;
    private final TimeUtils timeUtils;

    @Override
    public void getStudents(GetStudentsRequest request, StreamObserver<GetStudentsResponse> responseObserver) {
        long groupId = request.getGroupId();

        List<StudentEntity> studentEntities = studentDbService.getStudents(groupId);

        List<Student> students = studentEntities.stream()
                .map(studentMapper::map)
                .toList();
        GetStudentsResponse getStudentsResponse = GetStudentsResponse.newBuilder()
                .addAllStudent(students)
                .build();

        responseObserver.onNext(getStudentsResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void createStudent(CreateStudentRequest request, StreamObserver<CreateStudentResponse> responseObserver) {
        long groupId = request.getGroupId();
        String fio = request.getFio();
        LocalDate joinDate = timeUtils.toLocalDate(request.getJoinDate());

        StudentEntity studentEntity = studentDbService.createStudent(groupId, fio, joinDate);

        Student student = studentMapper.map(studentEntity);
        CreateStudentResponse createStudentResponse = CreateStudentResponse.newBuilder()
                .setStudent(student)
                .build();

        responseObserver.onNext(createStudentResponse);
        responseObserver.onCompleted();
    }
}
