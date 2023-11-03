package nss.capstone.activity;

import nss.capstone.activity.requests.CreateRecipeRequest;
import nss.capstone.activity.results.CreateRecipeResult;
import nss.capstone.dynamodb.RecipeDao;

import javax.inject.Inject;

public class CreateRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public CreateRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public CreateRecipeResult handleRequest(CreateRecipeRequest request) {
        
    }
}
