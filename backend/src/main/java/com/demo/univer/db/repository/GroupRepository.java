package com.demo.univer.db.repository;

import com.demo.univer.db.entity.GroupEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, Long> {
    // workaround for mysql id sequence
    @Query("SELECT LAST_INSERT_ID()")
    long getLastId();

    Optional<GroupEntity> findByName(String name);
}
