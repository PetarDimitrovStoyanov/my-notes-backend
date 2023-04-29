--liquibase formatted sql
--changeset petar_stoyanov:add_roles.sql

INSERT INTO `roles` (`name`)
VALUES ('Admin'),
       ('User');