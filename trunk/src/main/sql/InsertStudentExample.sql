INSERT INTO `cs160`.`students`
(`Email`,
`Password`,
`FName`,
`LName`,
`SID`,
`Verified`,
`AdvisorEmail`,
`MajorID`)
VALUES
("frankdaniels110@sjsu.edu",
"password1",
"Frank",
"Daniels",
"001712736",
'T',
"Ahmed.Yazdankah@sjsu.edu",
(select `MajorID` from major where `MajorName` = "Computer Science")
);