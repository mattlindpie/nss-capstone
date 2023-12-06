package nss.capstone.activity;


import nss.capstone.activity.requests.GetRecipesByCaloriesRequest;
import nss.capstone.activity.results.GetRecipesByCaloriesResult;
import nss.capstone.dynamodb.RecipeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GetRecipesByCaloriesActivityTest {

    private final String USER_ID = "userId";

    private Integer MIN_CALORIES;
    private Integer MAX_CALORIES;

    @Mock
    private RecipeDao recipeDao;

    GetRecipesByCaloriesActivity activity;

    @BeforeEach
    void setup() {
        openMocks(this);
        activity = new GetRecipesByCaloriesActivity(recipeDao);
    }

    @Test
    public void handleRequest_returnsListOfRecipes() {
        MIN_CALORIES = 100;
        MAX_CALORIES = 500;

        when(recipeDao.getRecipesByCalories(USER_ID, MIN_CALORIES, MAX_CALORIES)).thenReturn(new ArrayList<>());
        GetRecipesByCaloriesRequest request = GetRecipesByCaloriesRequest.builder()
                .withUserId(USER_ID)
                .withMinCalories(MIN_CALORIES)
                .withMaxCalories(MAX_CALORIES)
                .build();

        GetRecipesByCaloriesResult result = activity.handleRequest(request);

        assertNotNull(result);
    }
}