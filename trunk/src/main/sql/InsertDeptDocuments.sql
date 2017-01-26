INSERT INTO `cs160`.`majordocuments`
(`MajorID`,
`DocID`)
VALUES
((SELECT `MajorID` FROM `major` WHERE `MajorName` = "Computer Science"),
1);