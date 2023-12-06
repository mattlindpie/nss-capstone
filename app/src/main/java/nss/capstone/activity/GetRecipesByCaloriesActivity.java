package nss.capstone.activity;

import nss.capstone.activity.requests.GetRecipesByCaloriesRequest;
import nss.capstone.activity.results.GetRecipesByCaloriesResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.RecipeDao;
import nss.capstone.dynamodb.models.Recipe;

import javax.inject.Inject;
import java.util.List;

public class GetRecipesByCaloriesActivity {

    private final RecipeDao recipeDao;

    @Inject
    public GetRecipesByCaloriesActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public GetRecipesByCaloriesResult handleRequest(GetRecipesByCaloriesRequest request) {
        List<Recipe> recipeList = recipeDao.getRecipesByCalories(request.getUserId(), request.getMinCalories(), request.getMaxCalories());

        ModelConverter converter = new ModelConverter();
        recipeList.stream()
                .forEach(recipe -> converter.toRecipeModel(recipe));

        return GetRecipesByCaloriesResult.builder()
                .withRecipes(recipeList)
                .build();
    }
}
