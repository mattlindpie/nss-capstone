package nss.capstone.activity;

import nss.capstone.activity.requests.DeleteRecipeRequest;
import nss.capstone.activity.results.DeleteRecipeResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.models.RecipeModel;

import javax.inject.Inject;

public class DeleteRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public DeleteRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public DeleteRecipeResult handleRequest(DeleteRecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setUserId(request.getUserId());
        recipe.setRecipeName(request.getRecipeName());

        recipeDao.deleteRecipe(recipe);

        RecipeModel recipeModel = new ModelConverter().toRecipeModel(recipe);
        return DeleteRecipeResult.builder()
                .withRecipe(recipeModel)
                .build();
    }

}
