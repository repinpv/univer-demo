package com.demo.univer.db.projection;

public record StatGroupProjection(
        Long id,
        String name,
        Integer maxMemberCount,
        Integer memberCount
) {
}
