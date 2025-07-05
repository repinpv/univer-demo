package com.demo.univer.db.service;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.projection.StatGroupProjection;

import java.util.List;

public interface GroupDbService {
    List<StatGroupProjection> getAllGroups();

    GroupEntity create(String name, int maxMemberCount);

    GroupEntity getGroup(long groupId);
}
