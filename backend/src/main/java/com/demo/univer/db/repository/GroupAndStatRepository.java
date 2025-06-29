package com.demo.univer.db.repository;

import com.demo.univer.db.entity.StatGroupEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAndStatRepository extends org.springframework.data.repository.Repository<StatGroupEntity, Long> {
    @Query("""
SELECT g.*, s.member_count
    FROM tgroups g
    LEFT JOIN (
        SELECT group_id, COUNT(*) AS member_count FROM students GROUP BY group_id
    ) s ON g.id = s.group_id
    ORDER BY g.name
""")
    List<StatGroupEntity> getAll();
}
