package nss.capstone.activity;

import nss.capstone.activity.requests.CreateRecipeRequest;
import nss.capstone.activity.results.CreateRecipeResult;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Ingredient;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateRecipeRequestTest {

    @Mock
    private RecipeDao recipeDao;

    private CreateRecipeActivity recipeActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        recipeActivity = new CreateRecipeActivity(recipeDao);
    }

    @Test
    public void handleRequest_validData_createAndSaveRecipe() {

        String userId = "validId";
        String recipeName = "validName";
        List<Ingredient> ingredients = new ArrayList<>();
        List<String> recipeSteps = new ArrayList<>();
        Integer servings = 0;
        Integer calories = 0;

        CreateRecipeRequest request = CreateRecipeRequest.builder()
                .withUserId(userId)
                .withRecipeName(recipeName)
                .withIngredients(ingredients)
                .withRecipeSteps(recipeSteps)
                .withServings(servings)
                .withCalories(calories)
                .build();

        CreateRecipeResult result = recipeActivity.handleRequest(request);

        verify(recipeDao).saveRecipe(any(Recipe.class));

        assertEquals(recipeName, result.getRecipe().getRecipeName());
        assertEquals(ingredients, result.getRecipe().getIngredients());
        assertEquals(recipeSteps, result.getRecipe().getRecipeSteps());
        assertEquals(servings, result.getRecipe().getCalories());
        assertEquals(calories, result.getRecipe().getCalories());
    }
}