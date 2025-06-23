package com.demo.univer.grpc;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.service.GroupDbService;
import com.demo.univer.gprc.group.CreateGroupRequest;
import com.demo.univer.gprc.group.CreateGroupResponse;
import com.demo.univer.gprc.group.GetGroupsRequest;
import com.demo.univer.gprc.group.GetGroupsResponse;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.gprc.group.GroupAndStat;
import com.demo.univer.gprc.group.GroupServiceGrpc;
import com.demo.univer.mapper.GroupMapper;
import com.demo.univer.validator.GroupNameValidator;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GroupGrpcService extends GroupServiceGrpc.GroupServiceImplBase {
    private final GroupDbService groupDbService;
    private final GroupMapper groupMapper;
    private final GroupNameValidator groupNameValidator;

    @Override
    public void getGroups(GetGroupsRequest request, StreamObserver<GetGroupsResponse> responseObserver) {
        List<GroupAndStat> groups = groupDbService.getAllGroups()
                .stream()
                .map(groupMapper::map)
                .toList();

        GetGroupsResponse getGroupsResponse = GetGroupsResponse.newBuilder()
                .addAllGroup(groups)
                .build();

        responseObserver.onNext(getGroupsResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void createGroup(CreateGroupRequest request, StreamObserver<CreateGroupResponse> responseObserver) {
        String name = request.getName();
        groupNameValidator.validate(name);

        GroupEntity groupEntity = groupDbService.create(name);
        Group group = groupMapper.map(groupEntity);

        CreateGroupResponse createGroupResponse = CreateGroupResponse.newBuilder()
                .setGroup(group)
                .build();

        responseObserver.onNext(createGroupResponse);
        responseObserver.onCompleted();
    }
}
