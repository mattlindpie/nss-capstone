package nss.capstone.activity.results;

import nss.capstone.models.RecipeModel;

public class UpdateRecipeResult {

    private final RecipeModel recipe;

    public UpdateRecipeResult(RecipeModel recipe) {
        this.recipe = recipe;
    }

    public RecipeModel getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "UpdateRecipeResult{" +
                "recipe=" + recipe +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RecipeModel recipeModel;

        public UpdateRecipeResult.Builder withRecipe(RecipeModel recipeModel) {
            this.recipeModel = recipeModel;
            return this;
        }

        public UpdateRecipeResult build() {
            return new UpdateRecipeResult(recipeModel);
        }

    }
}
