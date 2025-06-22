package com.demo.univer.config;

import com.demo.univer.gprc.group.GroupServiceGrpc;
import com.demo.univer.gprc.student.StudentServiceGrpc;
import com.demo.univer.step.GroupSteps;
import com.demo.univer.step.StudentSteps;
import com.demo.univer.utils.TimeUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.autoconfigure.server.GrpcServerProperties;
import org.springframework.grpc.client.GrpcChannelFactory;

@TestConfiguration
public class StepConfiguration {
    @Bean
    GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub(
            GrpcChannelFactory channels,
            GrpcServerProperties grpcServerProperties
    ) {
        System.out.println("StepConfiguration.groupServiceBlockingStub: grpcServerProperties.getPort():" + grpcServerProperties.getPort());
        return GroupServiceGrpc.newBlockingStub(
                channels.createChannel(
                        "0.0.0.0:" + grpcServerProperties.getPort()));
    }

    @Bean
    StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub(
            GrpcChannelFactory channels,
            GrpcServerProperties grpcServerProperties
    ) {
        System.out.println("StepConfiguration.studentServiceBlockingStub: grpcServerProperties.getPort():" + grpcServerProperties.getPort());
        return StudentServiceGrpc.newBlockingStub(
                channels.createChannel(
                        "0.0.0.0:" + grpcServerProperties.getPort()));
    }

    @Bean
    GroupSteps groupSteps(GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub) {
        return new GroupSteps(groupServiceBlockingStub);
    }

    @Bean
    StudentSteps studentSteps(
            StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub,
            TimeUtils timeUtils
    ) {
        return new StudentSteps(studentServiceBlockingStub, timeUtils);
    }
}
