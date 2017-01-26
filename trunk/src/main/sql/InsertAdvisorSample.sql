INSERT INTO `cs160`.`advisors`
(`Email`,
`Password`,
`FName`,
`LName`,
`SID`,
`Verified`,
`MajorID`)
VALUES
("Ahmed.Yazdankah@sjsu.edu",
"Password",
"Ahmed",
"Yazdankah",
"001234567",
'T',
(SELECT MajorID FROM major WHERE MajorName = "Computer Science"));
