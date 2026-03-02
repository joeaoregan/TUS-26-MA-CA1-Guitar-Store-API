-- drop database guitardb;

-- CREATE DATABASE IF NOT EXISTS guitardb;

-- USE guitardb;

CREATE TABLE brand (
	id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100),
    created_at DATETIME NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_at DATETIME DEFAULT NULL,
    updated_by VARCHAR(100) DEFAULT NULL
);

CREATE TABLE guitar (
	id BIGINT AUTO_INCREMENT PRIMARY KEY ,
	model_name VARCHAR(100) NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
	manufacture_date DATE,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_at DATETIME DEFAULT NULL,
    updated_by VARCHAR(100) DEFAULT NULL,
    brand_id BIGINT, 
    FOREIGN KEY (brand_id) REFERENCES brand(id) ON DELETE CASCADE
);
