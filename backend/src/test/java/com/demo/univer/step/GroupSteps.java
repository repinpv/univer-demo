package com.demo.univer.step;

import com.demo.univer.gprc.group.CreateGroupRequest;
import com.demo.univer.gprc.group.CreateGroupResponse;
import com.demo.univer.gprc.group.GetGroupsRequest;
import com.demo.univer.gprc.group.GetGroupsResponse;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.gprc.group.GroupServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GroupSteps {
    private final GroupServiceGrpc.GroupServiceBlockingStub groupServiceBlockingStub;

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

    public void checkList(List<Group> groups) {
        GetGroupsRequest getGroupsRequest = GetGroupsRequest.newBuilder()
                .build();
        GetGroupsResponse getGroupsResponse = groupServiceBlockingStub.getGroups(getGroupsRequest);
        List<Group> responseGroups = getGroupsResponse.getGroupList();
        Assertions.assertEquals(groups.size(), responseGroups.size());

        Map<Long, Group> searchMap = groups.stream()
                .collect(Collectors.toMap(
                        Group::getId,
                        group -> group
                ));
        Assertions.assertEquals(groups.size(), searchMap.size());

        for (Group responseGroup : responseGroups) {
            Group group = searchMap.get(responseGroup.getId());
            Assertions.assertNotNull(group);

            Assertions.assertEquals(group.getName(), responseGroup.getName());
        }
    }
}
