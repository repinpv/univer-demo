package com.demo.univer.db.repository;

import com.demo.univer.db.entity.GroupEntity;
import com.demo.univer.db.projection.StatGroupProjection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, Long> {
    // workaround for mysql id sequence
    @Query("SELECT LAST_INSERT_ID()")
    long getLastId();

    Optional<GroupEntity> findByName(String name);

    @Query("""
SELECT g.*, s.member_count
    FROM tgroups g
    LEFT JOIN (
        SELECT group_id, COUNT(*) AS member_count FROM students GROUP BY group_id
    ) s ON g.id = s.group_id
    ORDER BY g.name
""")
    List<StatGroupProjection> getAllWithMemberCount();

    @Query("SELECT * FROM tgroups WHERE id = :groupId FOR UPDATE")
    Optional<GroupEntity> findByIdForUpdate(Long groupId);
}
