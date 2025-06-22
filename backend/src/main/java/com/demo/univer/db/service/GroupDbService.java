package com.demo.univer.db.service;

import com.demo.univer.db.entity.GroupEntity;

import java.util.List;

public interface GroupDbService {
    List<GroupEntity> getAllGroups();

    GroupEntity create(String name);
}
