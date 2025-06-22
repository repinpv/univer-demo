package com.demo.univer.db.service.impl;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.repository.GroupRepository;
import com.demo.univer.db.service.GroupDbService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDbServiceImpl implements GroupDbService {
    private final GroupRepository groupRepository;

    @Override
    public List<GroupEntity> getAllGroups() {
        return Lists.newArrayList(
                groupRepository.findAll());
    }

    @Override
    public GroupEntity create(String name) {
        GroupEntity groupEntity = GroupEntity.builder()
                .name(name)
                .build();

        groupRepository.save(groupEntity);
        groupEntity.setId(groupRepository.getLastId());

        return groupEntity;
    }
}
