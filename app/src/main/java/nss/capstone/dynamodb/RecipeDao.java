package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;
import javax.inject.Singleton;

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
}
