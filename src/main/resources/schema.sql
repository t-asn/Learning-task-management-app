DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS categories;

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    category_id INTEGER     NOT NULL REFERENCES categories (id),
    due_date    DATE        NOT NULL,
    status      VARCHAR(10) NOT NULL DEFAULT 'TODO',
    CONSTRAINT chk_status CHECK (status IN ('TODO', 'DOING', 'DONE'))
);