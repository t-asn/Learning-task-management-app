DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks
(
    id       SERIAL PRIMARY KEY,
    title    VARCHAR(50) NOT NULL,
    category VARCHAR(20) NOT NULL,
    due_date DATE        NOT NULL
);