package com.demo.univer.mapper;

import com.demo.univer.db.entity.GroupAndStatEntity;
import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.gprc.group.Group;
import com.demo.univer.gprc.group.GroupAndStat;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupMapper {
    public Group map(GroupEntity groupEntity) {
        return Group.newBuilder()
                .setId(groupEntity.getId())
                .setName(groupEntity.getName())
                .build();
    }

    public GroupAndStat map(GroupAndStatEntity groupAndStatEntity) {
        int memberCount = Optional.ofNullable(
                        groupAndStatEntity.getMemberCount())
                .orElse(0);

        return GroupAndStat.newBuilder()
                .setId(groupAndStatEntity.getId())
                .setName(groupAndStatEntity.getName())
                .setMemberCount(memberCount)
                .build();
    }
}
