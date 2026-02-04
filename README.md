📌 Project Overview

The HR Recruitment & Applicant Tracking System (ATS) is a Java and MySQL–based application designed to manage the recruitment process in an organization. It enables HR users to post jobs, track applications, and update candidate statuses, while candidates can register, log in, view available jobs, and apply for positions.
The project demonstrates strong integration between Java, JDBC, and relational databases.

🎯 Objectives

Automate the job recruitment process

Maintain structured records of users, jobs, and applications

Demonstrate DBMS concepts such as relationships, foreign keys, and normalization

Implement role-based access using Java and SQL

🛠️ Tech Stack

Programming Language: Java

Database: MySQL

Connectivity: JDBC (MySQL Connector/J)

Tools: VS Code, MySQL Workbench

Platform: Console-based application

👥 User Roles
🧑‍💼 HR

Login securely

Post new job openings

View candidate applications

Update application status (INTERVIEW / SELECTED / REJECTED)

👨‍🎓 Candidate

Register with email and password

Login to the system

View available jobs

Apply for jobs

View application status

🗂️ Database Design

The system uses a relational database with the following tables:

users – Stores HR and candidate details

jobs – Stores job postings created by HR

applications – Stores job applications submitted by candidates

All tables are connected using foreign key relationships to maintain data integrity.

⚙️ Setup Instructions
1️⃣ Database Setup

Open MySQL Workbench

Run the provided SQL script to create:

hr_ats database

users, jobs, and applications tables

Insert default HR and test candidate data

2️⃣ Java Setup

Place ATS.java and mysql-connector-j-9.6.0.jar in the same folder

Compile the program:

javac -cp ".;mysql-connector-j-9.6.0.jar" ATS.java


Run the program:

java -cp ".;mysql-connector-j-9.6.0.jar" ATS

🔐 Default Login Credentials
HR Login
Email: hr@company.com
Password: hr123

Candidate Login
Email: tarun@gmail.com
Password: tarun123

▶️ Application Flow

HR logs in and posts job openings

Candidate registers and logs in

Candidate views jobs and applies

HR reviews applications

HR updates application status

📸 Sample Features Demonstrated

JDBC connectivity with MySQL

Role-based authentication

CRUD operations using SQL

Input validation and error handling

Real-world recruitment workflow simulation

📈 Future Enhancements

Password encryption (hashing)

Graphical User Interface (Swing / JavaFX)

Web-based version using Servlets or Spring Boot

Resume upload functionality

Admin analytics dashboard

📚 Learning Outcomes

Practical understanding of Java–SQL integration

Hands-on experience with DBMS concepts

Experience in designing real-world applications

Improved debugging and problem-solving skills

👤 Author

Tarun Dubey
Computer Science Student
(Java | DBMS | MySQL)
