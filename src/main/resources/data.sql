INSERT INTO tasks (title, category, due_date)
VALUES ('Java学習', 'Java', '2026-05-01')
ON CONFLICT DO NOTHING;
INSERT INTO tasks (title, category, due_date)
VALUES ('Spring Boot構築', 'Spring', '2026-06-01')
ON CONFLICT DO NOTHING;