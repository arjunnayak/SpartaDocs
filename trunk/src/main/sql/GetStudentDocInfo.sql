SELECT `DocID`, `DocName`, `Date`
FROM `documents`
WHERE `DocID` = 
(
	SELECT `DocID` 
	FROM `userdocuments`
    WHERE `UserEmail` = "frank.daniels@sjsu.edu"    
);