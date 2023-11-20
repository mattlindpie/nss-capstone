package nss.capstone.activity;

import nss.capstone.activity.requests.GetRecipeRequest;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetRecipeActivityTest {

    @Mock
    RecipeDao recipeDao;

    GetRecipeActivity getRecipeActivity;

    @BeforeEach
    void setup() {
        openMocks(this);

        getRecipeActivity = new GetRecipeActivity(recipeDao);
    }

    @Test
    public void handleRequest_validRequest_returnsSpecifiedRecipe() {
        Recipe recipe = new Recipe();
        recipe.setUserId("validId");
        recipe.setRecipeName("validName");
        recipe.setRecipeSteps(new ArrayList<>());
        recipe.setCalories(0);
        recipe.setServings(0);
        recipe.setIngredients(new ArrayList<>());

        when(recipeDao.getRecipe(anyString(), anyString())).thenReturn(recipe);

        GetRecipeRequest request = GetRecipeRequest.builder()
                .withUserId("userId")
                .withRecipeName("recipeName")
                .build();

        getRecipeActivity.handleRequest(request);

        verify(recipeDao).getRecipe(anyString(), anyString());

    }

}