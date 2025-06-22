CREATE TABLE students
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    group_id  BIGINT       NOT NULL,
    fio       VARCHAR(500) NOT NULL,
    join_date DATE         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (group_id) REFERENCES tgroups (id)
);

CREATE INDEX student_fio_idx ON students (fio);