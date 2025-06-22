package com.demo.univer.mapper;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.gprc.group.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public Group map(GroupEntity groupEntity) {
        return Group.newBuilder()
                .setId(groupEntity.getId())
                .setName(groupEntity.getName())
                .build();
    }
}
