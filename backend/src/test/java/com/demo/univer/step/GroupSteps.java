package com.demo.univer.step;

import com.demo.univer.error.ErrorType;
import com.demo.univer.grpc.group.v1.CreateGroupRequest;
import com.demo.univer.grpc.group.v1.CreateGroupResponse;
import com.demo.univer.grpc.group.v1.ExtGroup;
import com.demo.univer.grpc.group.v1.GetGroupsRequest;
import com.demo.univer.grpc.group.v1.GetGroupsResponse;
import com.demo.univer.grpc.group.v1.Group;
import com.demo.univer.grpc.group.v1.GroupServiceGrpc;
import com.demo.univer.utils.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RequiredArgsConstructor
public class GroupSteps {
    private final GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub;
    private final ErrorUtils errorUtils;

    public void checkEmptyList() {
        checkList(List.of());
    }

    public void checkList(List<Group> groups) {
        checkList(groups, List.of());
    }

    public void checkList(List<Group> groups, List<Integer> memberCounts) {
        GetGroupsRequest getGroupsRequest = GetGroupsRequest.newBuilder()
                .build();
        GetGroupsResponse getGroupsResponse = groupServiceBlockingStub.getGroups(getGroupsRequest);
        List<ExtGroup> responseGroups = getGroupsResponse.getGroupList();
        Assertions.assertEquals(groups.size(), responseGroups.size());

        Map<Long, Group> searchMap = groups.stream()
                .collect(Collectors.toMap(
                        Group::getId,
                        group -> group
                ));
        Assertions.assertEquals(groups.size(), searchMap.size());

        for (ExtGroup responseGroup : responseGroups) {
            Group group = searchMap.get(responseGroup.getId());
            Assertions.assertNotNull(group);

            Assertions.assertEquals(group.getName(), responseGroup.getName());
        }

        Assertions.assertTrue(memberCounts.size() <= responseGroups.size());
        for (int i = 0; i < memberCounts.size(); i++) {
            ExtGroup statGroupModel = responseGroups.get(i);
            int count = memberCounts.get(i);

            Assertions.assertEquals(count, statGroupModel.getMemberCount());
        }
    }

    public Group createGroup(String name) {
        CreateGroupRequest createGroupRequest = CreateGroupRequest.newBuilder()
                .setName(name)
                .build();
        CreateGroupResponse createGroupResponse = groupServiceBlockingStub.createGroup(createGroupRequest);
        Group group = createGroupResponse.getGroup();

        Assertions.assertNotNull(group);
        Assertions.assertEquals(name, group.getName());

        return group;
    }

    public void createGroupError(String name, ErrorType errorType) {
        CreateGroupRequest createGroupRequest = CreateGroupRequest.newBuilder()
                .setName(name)
                .build();

        errorUtils.assertError(errorType, () -> groupServiceBlockingStub.createGroup(createGroupRequest));
    }
}
