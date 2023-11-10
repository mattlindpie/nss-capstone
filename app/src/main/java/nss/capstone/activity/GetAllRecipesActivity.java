package nss.capstone.activity;

import nss.capstone.activity.requests.GetAllRecipesRequest;
import nss.capstone.activity.results.GetAllRecipesResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;
import java.util.List;

public class GetAllRecipesActivity {

    private RecipeDao recipeDao;

    @Inject
    public GetAllRecipesActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public GetAllRecipesResult handleRequest(GetAllRecipesRequest request) {
        List<Recipe> recipeList = recipeDao.getAllRecipes(request.getUserId());

        recipeList.stream()
                .forEach(recipe -> new ModelConverter().toRecipeModel(recipe));

        return GetAllRecipesResult.builder()
                .withRecipes(recipeList)
                .build();
    }
}
