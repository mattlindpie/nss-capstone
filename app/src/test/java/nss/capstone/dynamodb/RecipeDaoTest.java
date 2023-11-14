package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeDaoTest {

    private final String USER_ID = "userId";
    private final String RECIPE_NAME = "recipeName";

    @Mock
    private DynamoDBMapper mapper;

    private RecipeDao recipeDao;

    @BeforeEach
    void setup() {
        openMocks(this);
        recipeDao = new RecipeDao(mapper);
    }

    @Test
    public void saveRecipe_callsMapper() {
        Recipe recipe = new Recipe();

        Recipe result = recipeDao.saveRecipe(recipe);

        verify(mapper).save(recipe);
        assertEquals(recipe, result);
    }

    @Test
    public void getAllRecipes_callsMapper() {

        recipeDao.getAllRecipes(USER_ID);

        verify(mapper).query(any(), any());
    }

    @Test
    public void deleteRecipe_callsMapper() {
        Recipe recipe = new Recipe();

        recipeDao.deleteRecipe(recipe);

        verify(mapper).delete(recipe);
    }

    @Test
    public void getRecipe_callsMapper() {

        when(mapper.load(any(), anyString(), anyString())).thenReturn(new Recipe());

        Recipe result = recipeDao.getRecipe(USER_ID, RECIPE_NAME);

        assertNotNull(result);
        verify(mapper).load(Recipe.class, USER_ID, RECIPE_NAME);
    }
}