--liquibase formatted sql
--changeset petar_stoyanov:add_users.sql

INSERT INTO `users` (`email`, `full_name`, `password`)
VALUES ('petar.dimitrov.stoyanov@gmail.com', 'Petar Stoyanov', '$2a$10$WEond.jthRlJL8OGsgCpROoeCjhltCz9FNKLhNHX9470Q6NmZtuIK'), /*123123*/
       ('ivan.ivanov@abv.bg', 'Ivan Ivanov', '$2a$10$0zPiGXY7j5bvZQSA4f0s.OZ//82MZZ/kNq2k.2bQoRitjqv0NhJMa'); /*qweqwe*/

