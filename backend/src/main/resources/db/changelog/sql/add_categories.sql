--liquibase formatted sql
--changeset petar_stoyanov:add_categories.sql

INSERT INTO `categories` (`name`)
VALUES ('Personal'),
       ('Job'),
       ('Other');