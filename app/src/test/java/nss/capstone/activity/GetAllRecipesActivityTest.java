package nss.capstone.activity;

import nss.capstone.activity.requests.GetAllRecipesRequest;
import nss.capstone.activity.results.GetAllRecipesResult;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetAllRecipesActivityTest {

    @Mock
    RecipeDao recipeDao;

    GetAllRecipesActivity getAllRecipesActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        getAllRecipesActivity = new GetAllRecipesActivity(recipeDao);
    }

    @Test
    public void handleRequest_multipleRecipes_returnsListOfRecipes() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        List<Recipe> recipeList = new ArrayList<>(Arrays.asList(recipe1, recipe2));

        when(recipeDao.getAllRecipes(anyString())).thenReturn(recipeList);
        GetAllRecipesRequest request = GetAllRecipesRequest.builder()
                .withUserId("userId")
                .build();

        GetAllRecipesResult result = getAllRecipesActivity.handleRequest(request);

        assertEquals(recipeList, result.getRecipes());
    }
}