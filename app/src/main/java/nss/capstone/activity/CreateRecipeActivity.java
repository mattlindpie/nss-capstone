package nss.capstone.activity;

import nss.capstone.activity.requests.CreateRecipeRequest;
import nss.capstone.activity.results.CreateRecipeResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.models.RecipeModel;

import javax.inject.Inject;

public class CreateRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public CreateRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public CreateRecipeResult handleRequest(CreateRecipeRequest request) {
        if (recipeDao.getRecipe(request.getUserId(), request.getRecipeName()) != null) {
            throw new RuntimeException("Recipe with that name already exists.");
        }

        Recipe newRecipe = new Recipe();
        newRecipe.setUserId(request.getUserId());
        newRecipe.setRecipeName(request.getRecipeName());
        newRecipe.setServings(request.getServings());
        newRecipe.setRecipeSteps(request.getRecipeSteps());
        newRecipe.setIngredients(request.getIngredients());
        newRecipe.setCalories(request.getCalories());
        recipeDao.saveRecipe(newRecipe);

        RecipeModel recipeModel = new ModelConverter().toRecipeModel(newRecipe);

        return CreateRecipeResult.builder()
                .withRecipe(recipeModel)
                .build();
    }
}
