package nss.capstone.activity.results;

import nss.capstone.dynamodb.models.Recipe;

import java.util.List;
import java.util.Objects;

public class GetAllRecipesResult {

    private final List<Recipe> recipes;


    public GetAllRecipesResult(List<Recipe> recipes) {
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

        public Builder withRecipes(List<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }

        public GetAllRecipesResult build() {
            return new GetAllRecipesResult(recipes);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Builder builder = (Builder) o;
        return Objects.equals(recipes, builder.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }

}
