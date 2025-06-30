package com.demo.univer.grpc;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.entity.StudentEntity;
import com.demo.univer.db.service.GroupDbService;
import com.demo.univer.db.service.StudentDbService;
import com.demo.univer.grpc.group.v1.Group;
import com.demo.univer.grpc.student.v1.CreateStudentRequest;
import com.demo.univer.grpc.student.v1.CreateStudentResponse;
import com.demo.univer.grpc.student.v1.GetStudentsRequest;
import com.demo.univer.grpc.student.v1.GetStudentsResponse;
import com.demo.univer.grpc.student.v1.Student;
import com.demo.univer.grpc.student.v1.StudentServiceGrpc;
import com.demo.univer.mapper.GroupMapper;
import com.demo.univer.mapper.StudentMapper;
import com.demo.univer.utils.TimeUtils;
import com.demo.univer.validator.StudentFioValidator;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class StudentGrpcService extends StudentServiceGrpc.StudentServiceImplBase {
    private final GroupDbService groupDbService;
    private final StudentDbService studentDbService;
    private final GroupMapper groupMapper;
    private final StudentMapper studentMapper;
    private final TimeUtils timeUtils;
    private final StudentFioValidator studentFioValidator;

    @Override
    public void getStudents(GetStudentsRequest request, StreamObserver<GetStudentsResponse> responseObserver) {
        long groupId = request.getGroupId();

        GroupEntity groupEntity = groupDbService.getGroup(groupId);
        Group group = groupMapper.map(groupEntity);

        List<StudentEntity> studentEntities = studentDbService.getStudents(groupId);
        List<Student> students = studentEntities.stream()
                .map(studentMapper::map)
                .toList();

        GetStudentsResponse getStudentsResponse = GetStudentsResponse.newBuilder()
                .setGroup(group)
                .addAllStudent(students)
                .build();

        responseObserver.onNext(getStudentsResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void createStudent(CreateStudentRequest request, StreamObserver<CreateStudentResponse> responseObserver) {
        String fio = request.getFio();
        studentFioValidator.validate(fio);

        long groupId = request.getGroupId();
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
