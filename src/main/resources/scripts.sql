drop database patientPortalDatabase;

create database if not exists `patientPortalDatabase`;


CREATE TABLE IF NOT EXISTS `patientPortalDatabase`.PATIENT 
(ID INT,
FIRSTNAME varchar(30),
LASTNAME varchar(30),
 ADDRESS varchar(80),
 EMAIL varchar(30),
 PHONENUMBER varchar(20),
 CITY varchar(20),
 STATE varchar(30),
 GENDER varchar(10),
 DOB datetime,
 SSN varchar(10), 
 PRIMARY KEY (ID));


CREATE TABLE IF NOT EXISTS `patientPortalDatabase`.PATIENTMEDICALHISTORY 
 (ID INT,
PATIENTID INT,
 HEIGHT varchar(20),
 WEIGHT varchar(20),
 BLOODPRESSURE varchar(20),
 PULSERATE varchar(20),
 AFFECTEDORGAN varchar(20),
 PRIMARY KEY (ID) ,
 FOREIGN KEY (PATIENTID) REFERENCES PATIENT(ID));




CREATE TABLE IF NOT EXISTS `patientPortalDatabase`.PHYSICIAN 
(ID INT,
NAME varchar(30),
PRIMARY KEY (ID));

CREATE TABLE IF NOT EXISTS `patientPortalDatabase`.APPOINTMENTSLOTS
(ID INT,
APPOINTMENTID varchar(20),
PATIENTID INT, 
PHYSICIANID INT,
PHYSICIANNAME varchar(30),
APPOINTMENTDATE datetime,
APPOINTMENTSLOT varchar(20), 
PRIMARY KEY (ID) ,
 FOREIGN KEY (PATIENTID) REFERENCES PATIENT(ID),
 FOREIGN KEY (PHYSICIANID) REFERENCES PHYSICIAN(ID));


INSERT INTO `patientPortalDatabase`.PHYSICIAN VALUES(1,"Ravi_Puri");
INSERT INTO `patientPortalDatabase`.PHYSICIAN VALUES(2,"Rahul");
INSERT INTO `patientPortalDatabase`.PHYSICIAN VALUES(3,"Vijay");
INSERT INTO `patientPortalDatabase`.PHYSICIAN VALUES(4,"Sanjana");
commit;