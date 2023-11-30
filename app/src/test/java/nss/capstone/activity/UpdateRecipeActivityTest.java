package nss.capstone.activity;

import nss.capstone.activity.requests.UpdateRecipeRequest;
import nss.capstone.activity.results.UpdateRecipeResult;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Ingredient;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateRecipeActivityTest {

    @Mock
    private RecipeDao recipeDao;

    private UpdateRecipeActivity updateRecipeActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        updateRecipeActivity = new UpdateRecipeActivity(recipeDao);
    }

    @Test
    public void handleRequest_changeValues_returnsUpdatedRecipe() {
        String userId = "validId";
        String recipeName = "validName";
        List<Ingredient> ingredients = new ArrayList<>();
        List<String> recipeSteps = new ArrayList<>();
        Integer servings = 0;
        Integer calories = 0;

        Recipe recipe = new Recipe();
        recipe.setUserId(userId);
        recipe.setRecipeName(recipeName);
        recipe.setRecipeSteps(recipeSteps);
        recipe.setIngredients(ingredients);
        recipe.setServings(servings);
        recipe.setCalories(calories);

        Ingredient ingredient = new Ingredient();
        Integer newCalories = 200;
        Integer newServings = 4;
        List<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(ingredient));
        List<String> newRecipeSteps = new ArrayList<>(Arrays.asList("mix", "bake"));

        UpdateRecipeRequest request = UpdateRecipeRequest.builder()
                .withUserId(userId)
                .withRecipeName(recipeName)
                .withIngredients(newIngredients)
                .withRecipeSteps(newRecipeSteps)
                .withServings(newServings)
                .withCalories(newCalories)
                .build();

        when(recipeDao.getRecipe(anyString(), anyString())).thenReturn(recipe);
        when(recipeDao.saveRecipe(recipe)).thenReturn(recipe);

        UpdateRecipeResult result = updateRecipeActivity.handleRequest(request);

        assertEquals(newCalories, result.getRecipe().getCalories());
        assertEquals(newServings, result.getRecipe().getServings());
        assertTrue(result.getRecipe().getRecipeSteps().size() > 0);
        assertTrue(result.getRecipe().getIngredients().size() > 0);
    }

}