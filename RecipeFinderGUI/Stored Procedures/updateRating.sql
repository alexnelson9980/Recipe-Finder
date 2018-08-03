USE `recipetest`;
DROP procedure IF EXISTS `updateRating`;

DELIMITER $$
USE `recipetest`$$
CREATE PROCEDURE `updateRating`(IN iUserID varchar(50), IN iRecipeID INT, IN iRating numeric(5,3), IN iIsDelete INT)
BEGIN
IF iIsDelete = 1 THEN
	
	/* Removing a rating from the database */
	DELETE FROM RATES_RECIPE
	WHERE RATES_RECIPE.Recipe_ID = iRecipeID
		AND RATES_RECIPE.User_ID = iUserID;
		
ELSE

	IF EXISTS (SELECT 1 FROM RATES_RECIPE WHERE User_ID = iUserID AND Recipe_ID = iRecipeID)
	THEN
	
		/* Updating an existing rating */
			UPDATE RATES_RECIPE
			SET RATES_RECIPE.Rating = iRating
			WHERE RATES_RECIPE.Recipe_ID = iRecipeID
				AND RATES_RECIPE.User_ID = iUserID;
	
	ELSE	
		
		/* Inserting a totally new rating */
		Insert Into RATES_RECIPE(Recipe_ID, User_ID, Rating)
		Values (iRecipeID, iUserID, iRating);
	
	END IF;
END IF;	
	
/* Update the stored rating in the recipe table */
UPDATE RECIPE
LEFT OUTER JOIN (
	SELECT Recipe_ID,
		AVG(Rating) AS 'Rating'
	FROM RATES_RECIPE
	WHERE Recipe_ID = iRecipeID
	GROUP BY Recipe_ID
	) New_Rating ON RECIPE.Recipe_ID = New_Rating.Recipe_ID
SET RECIPE.User_Rating = New_Rating.Rating
WHERE RECIPE.Recipe_ID = iRecipeID;

SELECT User_Rating FROM RECIPE WHERE Recipe_ID = iRecipeID;
END$$

DELIMITER ;

