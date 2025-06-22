package com.demo.univer.test;

import com.demo.univer.config.StepConfiguration;
import com.demo.univer.config.TestcontainersConfiguration;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.service.TestGroupDbService;
import com.demo.univer.step.GroupSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest(properties = {
        "spring.grpc.server.port=9091"}) //TODO This is working. But it is not good.
@Import({TestcontainersConfiguration.class, StepConfiguration.class})
class GroupTests {

    @Autowired
    private GroupSteps groupSteps;
    @Autowired
    private TestGroupDbService testGroupDbService;

    @BeforeEach
    void beforeEach() {
        testGroupDbService.deleteAllGroups();
    }

    @Test
    void checkEmptyGroupList() {
        groupSteps.checkEmptyList();
    }

    @Test
    void createGroup() {
        Group group = groupSteps.createGroup("TEST");
        groupSteps.checkList(List.of(group));
    }

    @Test
    void createTwoGroups() {
        Group group1 = groupSteps.createGroup("TEST1");
        Group group2 = groupSteps.createGroup("TEST2");
        groupSteps.checkList(List.of(group1, group2));
    }
}
