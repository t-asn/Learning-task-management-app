INSERT INTO categories (name)
VALUES ('Java'),
       ('Spring'),
       ('その他')
ON CONFLICT (name) DO NOTHING;