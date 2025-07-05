ALTER TABLE tgroups ADD COLUMN max_member_count INT;

UPDATE tgroups SET max_member_count = 50;

ALTER TABLE tgroups MODIFY max_member_count INT NOT NULL;
