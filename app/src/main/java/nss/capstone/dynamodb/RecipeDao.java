package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class RecipeDao {

    private final DynamoDBMapper mapper;

    @Inject
    public RecipeDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Recipe saveRecipe(Recipe recipe) {
        this.mapper.save(recipe);
        return recipe;
    }

    public List<Recipe> getAllRecipes(String userId) {

        Recipe recipe = new Recipe();
        recipe.setUserId(userId);

        DynamoDBQueryExpression<Recipe> queryExpression = new DynamoDBQueryExpression<Recipe>()
                .withHashKeyValues(recipe);

        return this.mapper.query(Recipe.class, queryExpression);
    }

    public List<Recipe> getRecipesByCalories (String userId, Integer minCalories, Integer maxCalories) {
        if(minCalories == null) {
            minCalories = 0;
        }
        if(maxCalories == null) {
            maxCalories = Integer.MAX_VALUE;
        }
        Map<String, com.amazonaws.services.dynamodbv2.model.AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        valueMap.put(":minCalories", new AttributeValue().withS(minCalories.toString()));
        valueMap.put(":maxCalories", new AttributeValue().withS(maxCalories.toString()));

        DynamoDBQueryExpression<Recipe> queryExpression = new DynamoDBQueryExpression<Recipe>()
                .withIndexName(Recipe.GSI_TABLE_NAME)
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId AND calories BETWEEN :minCalories AND :maxCalories")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Recipe> recipes = mapper.query(Recipe.class, queryExpression);
        return recipes == null ? new ArrayList<>() : recipes;

    }

    public void deleteRecipe(Recipe recipe) {
        mapper.delete(recipe);
    }
  
    public Recipe getRecipe(String userId, String recipeName) {

        return mapper.load(Recipe.class, userId, recipeName);
    }

}
