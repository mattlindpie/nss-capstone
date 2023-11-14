package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

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

    public void deleteRecipe(Recipe recipe) {
        mapper.delete(recipe);
    }
}
