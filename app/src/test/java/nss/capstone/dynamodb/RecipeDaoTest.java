package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeDaoTest {

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
}