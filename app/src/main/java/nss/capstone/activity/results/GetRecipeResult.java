package nss.capstone.activity.results;

import nss.capstone.models.RecipeModel;

public class GetRecipeResult {
    private final RecipeModel recipeModel;

    public GetRecipeResult(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    public RecipeModel getRecipe() {
        return recipeModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RecipeModel recipeModel;

        public Builder withRecipe(RecipeModel recipeModel) {
            this.recipeModel = recipeModel;
            return this;
        }

        public GetRecipeResult build() {
            return new GetRecipeResult(recipeModel);
        }
    }
}
