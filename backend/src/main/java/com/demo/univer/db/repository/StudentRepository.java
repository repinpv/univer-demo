package com.demo.univer.db.repository;

import com.demo.univer.db.entity.StudentEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
    @Query("SELECT LAST_INSERT_ID()")
    long getLastId();

    Iterable<StudentEntity> findAllByGroupId(long groupId);
}
