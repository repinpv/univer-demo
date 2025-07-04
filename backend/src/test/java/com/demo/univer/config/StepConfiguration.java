package com.demo.univer.config;

import com.demo.univer.grpc.group.v1.GroupServiceGrpc;
import com.demo.univer.grpc.student.v1.StudentServiceGrpc;
import com.demo.univer.step.GroupSteps;
import com.demo.univer.step.StudentSteps;
import com.demo.univer.utils.ErrorUtils;
import com.demo.univer.utils.TimeUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

@TestConfiguration
public class StepConfiguration {

    @Bean
    GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub(GrpcChannelFactory channels) {
        return GroupServiceGrpc.newBlockingStub(
                channels.createChannel("in-process"));
    }

    @Bean
    StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub(GrpcChannelFactory channels) {
        return StudentServiceGrpc.newBlockingStub(
                channels.createChannel("in-process"));
    }

    @Bean
    GroupSteps groupSteps(
            GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub,
            ErrorUtils errorUtils
    ) {
        return new GroupSteps(groupServiceBlockingStub, errorUtils);
    }

    @Bean
    StudentSteps studentSteps(
            StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub,
            TimeUtils timeUtils,
            ErrorUtils errorUtils
    ) {
        return new StudentSteps(studentServiceBlockingStub, timeUtils, errorUtils);
    }
}
