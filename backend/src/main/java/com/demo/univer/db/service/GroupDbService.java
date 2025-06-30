package com.demo.univer.db.service;

import com.demo.univer.db.entity.StatGroupEntity;
import com.demo.univer.db.entity.GroupEntity;

import java.util.List;

public interface GroupDbService {
    List<StatGroupEntity> getAllGroups();

    GroupEntity create(String name);

    void checkGroupExists(long groupId);

    GroupEntity getGroup(long groupId);
}
