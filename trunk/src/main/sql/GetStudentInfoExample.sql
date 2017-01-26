SELECT a.`FName`, a.`LName`, a.`SID`,c.`MajorName`, b.`FName` AS `AdvisorFName`, b.`LName` AS `AdvisorLName`
FROM students a, (
					SELECT *
                    FROM advisors
				 ) b,
                 (
                 SELECT * 
                 FROM major) c
WHERE a.`AdvisorEmail` = b.`Email` AND a.`Email` = "frankdaniels110@sjsu.edu" AND a.MajorID = c.MajorID;
                    