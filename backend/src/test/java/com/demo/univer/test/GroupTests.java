package com.demo.univer.test;

import com.demo.univer.config.StepConfiguration;
import com.demo.univer.config.TestcontainersConfiguration;
import com.demo.univer.error.ErrorType;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.service.TestGroupDbService;
import com.demo.univer.step.GroupSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
//@DirtiesContext // Ensure a clean context for each test
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

    @Test
    void notUniqueGroupName() {
        groupSteps.createGroup("TEST");
        groupSteps.createGroupError("TEST", ErrorType.GROUP_NAME_NOT_UNIQUE);
    }

    @Test
    void goodName() {
        groupSteps.createGroup("A1");
        groupSteps.createGroup("ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345");
        groupSteps.createGroup("A_1");
    }

    @Test
    void invalidName() {
        groupSteps.createGroupError("A", ErrorType.GROUP_NAME_FORMAT_INVALID);
        groupSteps.createGroupError("ABCDE12345ABCDE12345ABCDE12345ABCDE12345ABCDE12345A", ErrorType.GROUP_NAME_FORMAT_INVALID);
        groupSteps.createGroupError("a1", ErrorType.GROUP_NAME_FORMAT_INVALID);
        groupSteps.createGroupError("A_", ErrorType.GROUP_NAME_FORMAT_INVALID);
        groupSteps.createGroupError("A-", ErrorType.GROUP_NAME_FORMAT_INVALID);
    }
}
