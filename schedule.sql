CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE plans (
    plan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_text VARCHAR(500) NOT NULL,
    anonymity BOOLEAN DEFAULT true,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    user_id BIGINT, FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE SET NULL
);

CREATE TABLE delete_log(
	log_id bigint PRIMARY KEY AUTO_INCREMENT,
	entity_name VARCHAR(50) NOT NULL,
	entity_id BIGINT NOT NULL,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NOT NULL,
	CONSTRAINT unique_name_id UNIQUE (entity_name, entity_id)
);

CREATE INDEX idx_plans_anonymity ON plans(anonymity, updated_at);
CREATE INDEX idx_plans_user_id ON plans(user_id, updated_at);
