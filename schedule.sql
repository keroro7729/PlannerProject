CREATE TABLE plan (
    plan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_text VARCHAR(500) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);