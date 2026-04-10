INSERT INTO categories (name)
VALUES ('Java'),
       ('Spring'),
       ('その他');

INSERT INTO tasks (title, category_id, due_date)
VALUES ('Spring Bootの学習', 2, CURRENT_DATE + INTERVAL '7 days'),
       ('Java Silverの復習', 1, CURRENT_DATE + INTERVAL '3 days'),
       ('SQLのJOINを練習', 3, CURRENT_DATE);