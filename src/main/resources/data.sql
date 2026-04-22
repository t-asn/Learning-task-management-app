INSERT INTO categories (name)
VALUES ('Java'),
       ('Spring'),
       ('その他')
ON CONFLICT (name) DO NOTHING;

UPDATE tasks SET status = 'TODO' WHERE status = 'NOT_STARTED';
