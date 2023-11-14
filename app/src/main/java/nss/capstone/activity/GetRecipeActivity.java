package nss.capstone.activity;

import nss.capstone.activity.requests.GetRecipeRequest;
import nss.capstone.activity.results.GetRecipeResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.models.RecipeModel;

import javax.inject.Inject;

public class GetRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public GetRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public GetRecipeResult handleRequest(GetRecipeRequest request) {
        Recipe recipe = recipeDao.getRecipe(request.getUserId(), request.getRecipeName());

        RecipeModel recipeModel = new ModelConverter().toRecipeModel(recipe);
        return GetRecipeResult.builder()
                .withRecipe(recipeModel)
                .build();
    }

}
