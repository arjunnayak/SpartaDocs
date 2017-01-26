INSERT INTO `cs160`.`students`
(`Email`,
`Password`,
`FName`,
`LName`,
`SID`,
`Verified`,
`AdvisorEmail`,
`Major`,
`DeptID`)
VALUES
("Roberto.Parks@sjsu.edu",
"password3",
"Roberto",
"Parks",
"001234521",
'T',
"Ahmed.Yazdankah@sjsu.edu",
"Computer Science",
	(SELECT DeptID FROM `cs160`.`department` WHERE `DepartmentName` = "Computer Science")
);