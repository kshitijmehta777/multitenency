CREATE TABLE IF NOT EXISTS empolyees (
  employee_id int NOT NULL AUTO_INCREMENT,
  employee_name varchar(255) DEFAULT NULL,
  ctc int DEFAULT NULL,
  PRIMARY KEY (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;