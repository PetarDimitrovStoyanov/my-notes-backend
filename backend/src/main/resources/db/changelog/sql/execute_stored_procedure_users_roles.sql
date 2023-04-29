--liquibase formatted sql
--changeset petar_stoyanov:execute_stored_procedure_users_roles.sql

CALL MAP_USERS_ROLES();