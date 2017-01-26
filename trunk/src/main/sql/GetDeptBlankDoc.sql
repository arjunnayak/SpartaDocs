SELECT `DocID`, `DocName`, `Date`
FROM `documents`
WHERE `DocID` = 
(
	SELECT `DocID` 
	FROM `majordocuments`
    WHERE `MajorID` = 
    (
		SELECT `MajorID`
		FROM  `major`
        WHERE `MajorName` = "Computer Science"
	)
);