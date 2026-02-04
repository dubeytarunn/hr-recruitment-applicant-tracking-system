DROP DATABASE IF EXISTS hr_ats;
CREATE DATABASE hr_ats;
USE hr_ats;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('HR', 'CANDIDATE') NOT NULL
);

CREATE TABLE jobs (
    job_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    hr_id INT,
    FOREIGN KEY (hr_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    job_id INT,
    candidate_id INT,
    status VARCHAR(30),
    FOREIGN KEY (job_id) REFERENCES jobs(job_id)
        ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES users(user_id)
        ON DELETE CASCADE
);

INSERT INTO users (name, email, password, role)
VALUES ('HR Admin', 'hr@company.com', 'hr123', 'HR');

INSERT INTO users (name, email, password, role)
VALUES ('Tarun', 'tarun@gmail.com', 'tarun123', 'CANDIDATE');
