USE `recipetest`;
DROP procedure IF EXISTS `modifyUser`;

DELIMITER $$
USE `recipetest`$$
CREATE PROCEDURE `modifyUser`(IN iUserID VARCHAR(50), IN iUserPassword VARCHAR(50), IN iUserName VARCHAR(50), IN iIsDelete INT, IN iIsUpdate INT, OUT foundRows INT)
proc_label:BEGIN

/* Deleting user */
IF iIsDelete = 1 THEN
	DELETE FROM USER where User_ID = iUserID;
	LEAVE proc_label;
END IF;

/* Updating existing user */
IF iIsUpdate = 1 THEN
	UPDATE USER
	SET User_Name = iUserName, Password = iUserPassword
	WHERE User_ID = iUserID;
	LEAVE proc_label;
END IF;

/* Make sure user ID not already taken */
SELECT count(*) INTO foundRows
FROM (
	SELECT User_ID FROM USER
	WHERE User_ID = iUserID
	
	UNION ALL
	
	SELECT User_ID FROM rates_recipe
	WHERE User_ID = iUserID
	) AS T1;
	
IF foundRows > 0 THEN
	LEAVE proc_label;
END IF;

/* Checks passed, insert the user */
INSERT INTO USER (User_ID, User_Name, Password)
Values(iUserID, iUserName, iUserPassword);

END$$

DELIMITER ;

