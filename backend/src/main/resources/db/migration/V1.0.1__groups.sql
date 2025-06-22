CREATE TABLE tgroups
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX group_name_unique_idx ON tgroups (name);