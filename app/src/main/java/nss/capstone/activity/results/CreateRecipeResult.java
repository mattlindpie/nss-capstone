package nss.capstone.activity.results;

import nss.capstone.models.RecipeModel;
import org.apache.commons.lang3.builder.Builder;

public class CreateRecipeResult {

    private final RecipeModel recipe;

    public CreateRecipeResult(RecipeModel recipe) {
        this.recipe = recipe;
    }

    public RecipeModel getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "CreateRecipeResult{" +
                "recipe=" + recipe +
                '}';
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

        public CreateRecipeResult build() {
            return new CreateRecipeResult(recipeModel);
        }

    }
}
