USE `recipetest`;
DROP procedure IF EXISTS `editFavoriteRecipe`;

DELIMITER $$
USE `recipetest`$$
CREATE PROCEDURE `editFavoriteRecipe`(IN iUserID VARCHAR(50), IN iRecipeID INT, IN iIsRemove INT)
proc_label: BEGIN
	IF iIsRemove = 1 THEN

	DELETE FROM FAVORITE_RECIPE
	WHERE User_ID = iUserID
	AND Recipe_ID = iRecipeID;
	
	LEAVE proc_label;
	
END IF;

/* We are adding a favorite recipe */
IF NOT EXISTS(
	SELECT 1 FROM Favorite_Recipe
	WHERE User_ID = iUserID AND Recipe_ID = iRecipeID
	)
THEN

	INSERT INTO Favorite_Recipe (User_ID, Recipe_ID)
	VALUES (iUserID, iRecipeID);

END IF;

END$$

DELIMITER ;

