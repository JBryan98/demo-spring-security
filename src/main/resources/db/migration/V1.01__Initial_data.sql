INSERT INTO users(first_name, last_name, email, username, password, created_by,
                  last_modified_by,
                  created_at,
                  last_modified_at)
VALUES ('Bryan', 'Corrales', 'bcorrales@test.mail', 'bcorrales',
        '$2a$10$.cw9tPmeH8b7V85Mmo5UCurhhSNpYafQEyYuX3YZObvFhQSfT4xSe', 'bcorrales', NULL,
        CURRENT_TIMESTAMP, NULL);

INSERT INTO roles(name, created_by, last_modified_by, created_at, last_modified_at)
VALUES ('ROLE_ADMIN', 'bcorrales', NULL, CURRENT_TIMESTAMP, NULL),
       ('ROLE_USER', 'bcorrales', NULL, CURRENT_TIMESTAMP, NULL);


INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
        (1, 2);