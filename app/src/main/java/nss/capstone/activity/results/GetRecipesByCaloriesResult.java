package nss.capstone.activity.results;

import nss.capstone.dynamodb.models.Recipe;

import java.util.List;
import java.util.Objects;

public class GetRecipesByCaloriesResult {

    private final List<Recipe> recipes;


    public GetRecipesByCaloriesResult(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Recipe> recipes;

        public GetRecipesByCaloriesResult.Builder withRecipes(List<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }

        public GetRecipesByCaloriesResult build() {
            return new GetRecipesByCaloriesResult(recipes);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetRecipesByCaloriesResult that = (GetRecipesByCaloriesResult) o;
        return Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }
}
