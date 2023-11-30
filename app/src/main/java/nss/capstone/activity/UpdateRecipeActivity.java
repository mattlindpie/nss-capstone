package nss.capstone.activity;

import nss.capstone.activity.requests.UpdateRecipeRequest;
import nss.capstone.activity.results.UpdateRecipeResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;

public class UpdateRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public UpdateRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public UpdateRecipeResult handleRequest(UpdateRecipeRequest request) {
        Recipe recipe = recipeDao.getRecipe(request.getUserId(), request.getRecipeName());

        recipe.setRecipeSteps(request.getRecipeSteps());
        recipe.setIngredients(request.getIngredients());
        recipe.setServings(request.getServings());
        recipe.setCalories(request.getCalories());

        recipe = recipeDao.saveRecipe(recipe);

        return UpdateRecipeResult.builder()
                .withRecipe(new ModelConverter().toRecipeModel(recipe))
                .build();
    }
}
