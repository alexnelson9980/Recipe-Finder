CREATE PROCEDURE `recipeSearch`(IN iUserID VARCHAR(50), IN iIncludeIngredients VARCHAR(100), IN iExcludeIngredients VARCHAR(100),
								IN iCategories VARCHAR(1000), IN maxSodium decimal(5,0), IN minSodium decimal(5,0), In maxCalories int,
                                IN minCalories INT, in maxFat decimal(5,2), in minFat decimal(5,2), in maxProtein decimal(4,1), in minProtein decimal(4,2))
BEGIN
SELECT
	RECIPE.Recipe_ID,
	MAX(RECIPE.Title) as "Title",
	MAX(RECIPE.Epicurious_Rating) as "Epicurious_Rating",
	MAX(RECIPE.User_Rating) as "User_Rating",
    MAX(UserFaves.Is_Fav) as "Is_Fav",
	COUNT(DISTINCT BASE_INGREDIENT.Ingredient_ID) as "Num_Ingredients",
    ExcludeTable.Exclude_ID
FROM
	RECIPE
	INNER JOIN CONTAINS ON RECIPE.Recipe_ID = CONTAINS.Recipe_ID
	INNER JOIN BASE_INGREDIENT ON CONTAINS.Ingredient_ID = BASE_INGREDIENT.Ingredient_ID
    INNER JOIN TYPE_OF ON TYPE_OF.Specific_ID = BASE_INGREDIENT.Ingredient_ID
    LEFT OUTER JOIN (
		SELECT DISTINCT Recipe_ID as Exclude_ID 
        FROM CONTAINS INNER JOIN TYPE_OF ON TYPE_OF.Specific_ID = CONTAINS.Ingredient_ID
        WHERE (FIND_IN_SET(TYPE_OF.Specific_ID, iExcludeIngredients) OR FIND_IN_SET(TYPE_OF.Generic_ID, iExcludeIngredients))
	) ExcludeTable ON RECIPE.Recipe_ID = ExcludeTable.Exclude_ID 
	LEFT OUTER JOIN BELONGS_TO ON RECIPE.Recipe_ID = BELONGS_TO.Recipe_ID
	LEFT OUTER JOIN (
		SELECT Recipe_ID,
			Recipe_ID as "Is_Fav"
		FROM FAVORITE_RECIPE
		WHERE User_ID = iUserID
	) UserFaves ON RECIPE.Recipe_ID = UserFaves.Recipe_ID
    
    WHERE 
		/* Filter on ingredients */
		(iIncludeIngredients = -1 OR FIND_IN_SET(TYPE_OF.Specific_ID, iIncludeIngredients) OR FIND_IN_SET(TYPE_OF.Generic_ID, iIncludeIngredients))
        AND ExcludeTable.Exclude_ID IS NULL
		/*AND (iExcludeIngredients = '-1' OR NOT FIND_IN_SET(Base_Ingredient.Ingredient_ID, iExcludeIngredients))*/
	
		/* Filter on categories */
		AND (iCategories = '-1' OR FIND_IN_SET(BELONGS_TO.Classification_Title, iCategories))
	
		/* Filter on sodium */
		AND (maxSodium = -1 OR RECIPE.Sodium <= maxSodium)
		AND (minSodium = -1 OR RECIPE.Sodium >= minSodium)
	
		/* Filter on Calories */
		AND (maxCalories = -1 OR RECIPE.Calories <= maxCalories)
		AND (minCalories = -1 OR RECIPE.Calories >= minCalories)
	
		/* Filter on fat */
		AND (maxFat = -1 OR RECIPE.Fat <= maxFat)
		AND (minFat = -1 OR RECIPE.Fat >= minFat)
	
		/* Filter on protein */
		AND (minProtein = -1 OR RECIPE.Protein >= minProtein)
		AND (minProtein = -1 OR RECIPE.Protein <= maxProtein)

GROUP BY
	RECIPE.Recipe_ID
ORDER BY
	COUNT(DISTINCT BASE_INGREDIENT.Ingredient_ID) DESC;

END