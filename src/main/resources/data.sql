-- 初期テストデータの投入
INSERT INTO tasks (title, category, due_date)
VALUES
    ('Spring Bootの学習', 'Spring', CURRENT_DATE + INTERVAL '7 days'),
    ('Java Silverの復習', 'Java', CURRENT_DATE + INTERVAL '3 days'),
    ('SQLのJOINを練習', 'その他', CURRENT_DATE),
    ('インフラ構成図の作成', 'その他', CURRENT_DATE + INTERVAL '10 days'),
    ('ページネーションの実装', 'Spring', CURRENT_DATE + INTERVAL '1 days');