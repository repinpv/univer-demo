package com.demo.univer.db.service.impl;

import com.demo.univer.db.entity.StatGroupEntity;
import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.repository.GroupAndStatRepository;
import com.demo.univer.db.repository.GroupRepository;
import com.demo.univer.db.service.GroupDbService;
import com.demo.univer.error.ErrorFactory;
import com.demo.univer.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDbServiceImpl implements GroupDbService {
    private final GroupRepository groupRepository;
    private final GroupAndStatRepository groupAndStatRepository;
    private final ErrorFactory errorFactory;

    @Override
    public List<StatGroupEntity> getAllGroups() {
        return groupAndStatRepository.getAll();
    }

    @Override
    public GroupEntity create(String name) {
        GroupEntity groupEntity = GroupEntity.builder()
                .name(name)
                .build();

        try {
            groupRepository.save(groupEntity);
        } catch (DbActionExecutionException e) {
            throw errorFactory.create(ErrorType.GROUP_NAME_NOT_UNIQUE, e);
        }
        groupEntity.setId(groupRepository.getLastId());

        return groupEntity;
    }

    @Override
    public void checkGroupExists(long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() ->
                        errorFactory.create(ErrorType.GROUP_NOT_FOUND));
    }
}
