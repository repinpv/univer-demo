package com.demo.univer.step;

import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import com.demo.univer.gprc.group.CreateGroupRequest;
import com.demo.univer.gprc.group.CreateGroupResponse;
import com.demo.univer.gprc.group.GetGroupsRequest;
import com.demo.univer.gprc.group.GetGroupsResponse;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.gprc.group.GroupAndStat;
import com.demo.univer.gprc.group.GroupServiceGrpc;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GroupSteps {
    private final GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub;
    private final ErrorFactory errorFactory;

    public void checkEmptyList() {
        GetGroupsRequest getGroupsRequest = GetGroupsRequest.newBuilder()
                .build();
        GetGroupsResponse getGroupsResponse = groupServiceBlockingStub.getGroups(getGroupsRequest);

        Assertions.assertTrue(getGroupsResponse.getGroupList().isEmpty());
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

        StatusRuntimeException statusRuntimeException = Assertions.assertThrows
                (StatusRuntimeException.class,
                        () -> groupServiceBlockingStub.createGroup(createGroupRequest));

        Assertions.assertEquals(errorType.getStatus().getCode(), statusRuntimeException.getStatus().getCode());
        Assertions.assertEquals(errorType.getErrorCode(), errorFactory.getErrorCode(statusRuntimeException));
    }

    public void checkList(List<Group> groups) {
        checkList(groups, List.of());
    }

    public void checkList(List<Group> groups, List<Integer> memberCounts) {
        GetGroupsRequest getGroupsRequest = GetGroupsRequest.newBuilder()
                .build();
        GetGroupsResponse getGroupsResponse = groupServiceBlockingStub.getGroups(getGroupsRequest);
        List<GroupAndStat> responseGroups = getGroupsResponse.getGroupList();
        Assertions.assertEquals(groups.size(), responseGroups.size());

        Map<Long, Group> searchMap = groups.stream()
                .collect(Collectors.toMap(
                        Group::getId,
                        group -> group
                ));
        Assertions.assertEquals(groups.size(), searchMap.size());

        for (GroupAndStat responseGroup : responseGroups) {
            Group group = searchMap.get(responseGroup.getId());
            Assertions.assertNotNull(group);

            Assertions.assertEquals(group.getName(), responseGroup.getName());
        }

        Assertions.assertTrue(memberCounts.size() <= responseGroups.size());
        for (int i = 0; i < memberCounts.size(); i++) {
            GroupAndStat groupAndStat = responseGroups.get(i);
            int count = memberCounts.get(i);

            Assertions.assertEquals(count, groupAndStat.getMemberCount());
        }
    }
}
