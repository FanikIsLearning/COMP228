DROP TABLE Employee;

CREATE TABLE Employee (
    EmpId NUMBER (6) PRIMARY KEY,
    EmpName VARCHAR (35) NOT NULL, 
    EmpGrSal NUMBER (8,2) NOT NULL
);
    
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (1,'HOI KIT FAN',120000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (2,'IVAN LIM',115000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (3,'BILLY HOI',112000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (4,'TIM WU',130000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (5,'AMY WONG',105000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (6,'SUHA PARK',112000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (7,'SELENE',130000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (8,'OMG',105000);
INSERT INTO Employee (EmpId, EmpName, EmpGrSal) VALUES (9,'NICE',100000);

COMMIT;


--******************************

CREATE TABLE Students (
   studentID char(9) NOT NULL,
   firstName varchar (20) NOT NULL,
   lastName varchar (20) NOT NULL,
   address varchar (30) NOT NULL,
   city varchar(30) NOT NULL,
   province char(2) NOT NULL,
   postalCode char(6) NOT NULL,
   PRIMARY KEY (studentID)
);
