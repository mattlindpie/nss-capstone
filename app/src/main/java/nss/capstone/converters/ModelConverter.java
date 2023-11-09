package nss.capstone.converters;

import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.models.RecipeModel;

public class ModelConverter {

    public RecipeModel toRecipeModel(Recipe recipe) {
        return RecipeModel.builder()
                .withUserId(recipe.getUserId())
                .withRecipeName(recipe.getRecipeName())
                .withServings(recipe.getServings())
//                .withRecipeSteps(recipe.getRecipeSteps())
                .withIngredients(recipe.getIngredients())
                .withCalories(recipe.getCalories())
                .build();
    }
}
