package com.demo.univer.mapper;

import com.demo.univer.db.entity.StudentEntity;
import com.demo.univer.gprc.student.Student;
import com.demo.univer.utils.TimeUtils;
import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    private final TimeUtils timeUtils;

    public Student map(StudentEntity studentEntity) {
        Timestamp joinDate = timeUtils.toTimestamp(
                studentEntity.getJoinDate());

        return Student.newBuilder()
                .setId(studentEntity.getId())
                .setFio(studentEntity.getFio())
                .setJoinDate(joinDate)
                .build();
    }
}
