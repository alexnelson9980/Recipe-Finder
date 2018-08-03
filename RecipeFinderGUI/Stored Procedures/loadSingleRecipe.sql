USE `recipetest`;
DROP procedure IF EXISTS `loadSingleRecipe`;

DELIMITER $$
USE `recipetest`$$
CREATE PROCEDURE `loadSingleRecipe`(IN iRecipeID INT, IN iUserID VARCHAR(50), INOUT oClassificationList VARCHAR(1000), OUT oUserRating INT)
BEGIN
/* Variable declarations */
DECLARE isDone integer default 0;
DECLARE currentClassification VARCHAR(50) default "";

DECLARE classificationCursor cursor for
	SELECT Classification_Title FROM BELONGS_TO WHERE Recipe_ID = iRecipeID;
	
DECLARE CONTINUE HANDLER
FOR NOT FOUND SET isDone = 1;

/* Loop and load list of classifications */

OPEN classificationCursor;
getList: LOOP

	FETCH classificationCursor into currentClassification;
	
	IF isDone = 1 THEN
		LEAVE getList;
	END IF;
	
	SET oClassificationList = concat(oClassificationList, "|", currentClassification);
	
END LOOP getList;
CLOSE classificationCursor;

/* Now select the other data into an implied table object */
SELECT
	RECIPE.Title,
    RECIPE.Calories,
	RECIPE.Sodium,
	RECIPE.Fat,
	RECIPE.Protein,
	RECIPE.Description,
	RECIPE.Instructions,
	RECIPE.Prepared_Ingredients,
	RECIPE.Epicurious_Rating,
	RECIPE.User_Rating
FROM
	RECIPE
WHERE
	RECIPE.Recipe_ID = iRecipeID;
    
SELECT
	RATES_RECIPE.Rating INTO oUserRating
FROM 
    RATES_RECIPE
WHERE
	RATES_RECIPE.Recipe_ID = iRecipeID and RATES_RECIPE.User_ID = iUserID;
		

END$$

DELIMITER ;

