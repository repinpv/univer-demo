package com.demo.univer.db.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@Table("students")
public class StudentEntity {
    @Id
    @Column("id")
    Long id;
    @Column("group_id")
    Long groupId;
    @Column("fio")
    String fio;
    @Column("join_date")
    LocalDate joinDate;
}
