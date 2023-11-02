package nss.capstone.activity;

import nss.capstone.dynamodb.RecipeDao;

import javax.inject.Inject;

public class CreateRecipeActivity {

    private final RecipeDao recipeDao;

    @Inject
    public CreateRecipeActivity(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }
}
