package nss.capstone.activity;

import nss.capstone.activity.requests.DeleteRecipeRequest;
import nss.capstone.activity.results.DeleteRecipeResult;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteRecipeActivityTest {

    @Mock
    RecipeDao recipeDao;

    DeleteRecipeActivity deleteRecipeActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        deleteRecipeActivity = new DeleteRecipeActivity(recipeDao);
    }

    @Test
    public void handleRequest_recipeExists_deletedFromTable() {
        String userId = "validId";
        String recipeName = "validName";

        DeleteRecipeRequest request = DeleteRecipeRequest.builder()
                .withUserId(userId)
                .withRecipeName(recipeName)
                .build();

        DeleteRecipeResult result = deleteRecipeActivity.handleRequest(request);

        assertEquals(recipeName, result.getRecipeModel().getRecipeName());

        verify(recipeDao).deleteRecipe(any(Recipe.class));
    }
}