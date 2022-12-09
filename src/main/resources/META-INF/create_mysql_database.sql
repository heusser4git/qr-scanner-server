-- first install and start a mysql server on your machine
-- then:
-- $ mysql -h localhost -uroot -p < src/main/resources/META-INF/create_mysql_database.sql
CREATE DATABASE IF NOT EXISTS personal;
DROP USER IF EXISTS 'personal'@'localhost';
DROP DATABASE IF EXISTS personal;

CREATE USER 'personal'@'localhost' IDENTIFIED BY '123456';
CREATE DATABASE personal DEFAULT CHARACTER SET utf8;
GRANT INSERT,SELECT,UPDATE,DELETE,DROP,CREATE ON personal.* TO 'personal'@'localhost';
FLUSH PRIVILEGES;

USE personal;
DROP TABLE IF EXISTS tblPerson;
CREATE TABLE IF NOT EXISTS tblPerson (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL,
    `vorname` VARCHAR(255) NULL,
    `geburtsdatum` DATE NULL,
    `status` TINYINT NOT NULL DEFAULT 1,
    `count` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`));