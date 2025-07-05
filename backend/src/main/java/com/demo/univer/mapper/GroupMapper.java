package com.demo.univer.mapper;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.projection.StatGroupProjection;
import com.demo.univer.grpc.group.v1.ExtGroup;
import com.demo.univer.grpc.group.v1.Group;
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

    public ExtGroup map(StatGroupProjection statGroupProjection) {
        int memberCount = Optional.ofNullable(
                        statGroupProjection.memberCount())
                .orElse(0);

        return ExtGroup.newBuilder()
                .setId(statGroupProjection.id())
                .setName(statGroupProjection.name())
                .setMemberCount(memberCount)
                .build();
    }
}
