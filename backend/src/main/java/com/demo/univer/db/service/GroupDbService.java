package com.demo.univer.db.service;

import com.demo.univer.db.entity.GroupAndStatEntity;
import com.demo.univer.db.entity.GroupEntity;

import java.util.List;

public interface GroupDbService {
    List<GroupAndStatEntity> getAllGroups();

    GroupEntity create(String name);

    void checkGroupExists(long groupId);
}
