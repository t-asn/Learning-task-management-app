CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    category_id INTEGER      NOT NULL REFERENCES categories (id),
    due_date    DATE         NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'NOT_STARTED',
    -- DOING を追加
    CONSTRAINT chk_status CHECK (status IN ('NOT_STARTED', 'DOING', 'DONE'))
);