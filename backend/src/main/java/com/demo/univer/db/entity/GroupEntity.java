package com.demo.univer.db.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("tgroups")
public class GroupEntity {
    @Id
    @Column("id")
    Long id;
    @Column("name")
    String name;
}
