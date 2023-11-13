package nss.capstone.converters;

import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.models.RecipeModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    public void toRecipeModel_withStepsAndIngredients_convertsRecipe() {
        Recipe recipe = new Recipe();
        recipe.setUserId("validId");
        recipe.setRecipeName("validName");
        recipe.setIngredients(new ArrayList<>());
        recipe.setRecipeSteps(new ArrayList<>());
        recipe.setServings(4);
        recipe.setCalories(100);

        RecipeModel recipeModel = modelConverter.toRecipeModel(recipe);
        assertEquals(recipe.getUserId(), recipeModel.getUserId());
        assertEquals(recipe.getRecipeName(), recipeModel.getRecipeName());
        assertEquals(recipe.getIngredients(), recipeModel.getIngredients());
        assertEquals(recipe.getRecipeSteps(), recipeModel.getRecipeSteps());
        assertEquals(recipe.getServings(), recipeModel.getServings());
        assertEquals(recipe.getCalories(), recipeModel.getCalories());

    }
}