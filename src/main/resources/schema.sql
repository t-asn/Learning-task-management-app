CREATE TABLE IF NOT EXISTS tasks
(
    id
             SERIAL
        PRIMARY
            KEY,
    title
             VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    due_date DATE        NOT NULL
);