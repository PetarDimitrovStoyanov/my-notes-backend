--liquibase formatted sql
--changeset petar_stoyanov:create_stored_procedure_users_roles.sql

DROP PROCEDURE IF EXISTS MAP_USERS_ROLES;
--changeset petar_stoyanov:create_stored_procedure_users_roles.sql_setDelimiter endDelimiter:/ rollbackEndDelimiter:/

CREATE PROCEDURE MAP_USERS_ROLES()
BEGIN

DECLARE adminUserId BIGINT DEFAULT (SELECT id FROM users WHERE email LIKE 'petar.dimitrov.stoyanov@gmail.com');
DECLARE normalUserId BIGINT DEFAULT (SELECT id FROM users WHERE email LIKE 'ivan.ivanov@abv.bg');

DECLARE adminRoleId BIGINT DEFAULT (SELECT id FROM roles WHERE `name` LIKE 'Admin');
DECLARE userRoleId BIGINT DEFAULT (SELECT id FROM roles WHERE `name` LIKE 'User');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES (adminUserId, adminRoleId),
       (adminUserId, userRoleId),
       (normalUserId, userRoleId);

END;
/

--rollback endDelimiter:;