package nss.capstone.activity.results;

import nss.capstone.models.RecipeModel;

public class DeleteRecipeResult {


    private final RecipeModel recipeModel;

    private DeleteRecipeResult(RecipeModel recipeModel){this.recipeModel = recipeModel;}

    public RecipeModel getRecipeModel(){return recipeModel;}


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RecipeModel recipeModel;

        public Builder withRecipe(RecipeModel recipeModel) {
            this.recipeModel = recipeModel;
            return this;
        }

        public DeleteRecipeResult build() {return new DeleteRecipeResult(recipeModel);}
    }
}
