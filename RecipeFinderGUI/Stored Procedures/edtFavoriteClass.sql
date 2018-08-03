USE `recipetest`;
DROP procedure IF EXISTS `editFavoriteClass`;

DELIMITER $$
USE `recipetest`$$
CREATE PROCEDURE `editFavoriteClass`(IN iUserID VARCHAR(50), IN iClassificationTitle VARCHAR(50), IN iIsRemove INT)
start_proc: BEGIN
IF iIsRemove = 1 THEN

	DELETE FROM FAVORITE_CLASSIFICATION
	WHERE User_ID = iUserID
	AND Classification_Title = iClassificationTitle;
	
	LEAVE start_proc;
	
END IF;

/* We are adding a favorite classification */
IF NOT EXISTS(
	SELECT 1 FROM FAVORITE_CLASSIFICATION
	WHERE User_ID = iUserID AND Classification_Title = iClassificationTitle
	)
THEN

	INSERT INTO FAVORITE_CLASSIFICATION (User_ID, Classification_Title)
	VALUES (iUserID, iClassificationTitle);

END IF;
END$$

DELIMITER ;

