package nss.capstone.models;

import nss.capstone.dynamodb.models.Ingredient;
import org.apache.commons.lang3.builder.Builder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecipeModel {

    private final String userId;
    private final String recipeName;
    private final Integer servings;
//    private final Map<Integer, String> recipeSteps;
    private final List<Ingredient> ingredients;
    private final Integer calories;

    public RecipeModel(String userId, String recipeName, Integer servings,
                       List<Ingredient> ingredients, Integer calories) {
        this.userId = userId;
        this.recipeName = recipeName;
        this.servings = servings;
//        this.recipeSteps = recipeSteps;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public String getUserId() {
        return userId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Integer getServings() {
        return servings;
    }

//    public Map<Integer, String> getRecipeSteps() {
//        return recipeSteps;
//    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Integer getCalories() {
        return calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeModel that = (RecipeModel) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(recipeName, that.recipeName) &&
                Objects.equals(servings, that.servings) &&
//                Objects.equals(recipeSteps, that.recipeSteps) &&
                Objects.equals(ingredients, that.ingredients) &&
                Objects.equals(calories, that.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recipeName, servings, ingredients, calories);
    }

    @Override
    public String toString() {
        return "RecipeModel{" +
                "userId='" + userId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", servings=" + servings +
//                ", recipeSteps=" + recipeSteps +
                ", ingredients=" + ingredients +
                ", calories=" + calories +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String recipeName;
        private Integer servings;
//        private Map<Integer, String> recipeSteps;
        private List<Ingredient> ingredients;
        private Integer calories;

        public RecipeModel.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public RecipeModel.Builder withRecipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public RecipeModel.Builder withServings(Integer servings) {
            this.servings = servings;
            return this;
        }

//        public RecipeModel.Builder withRecipeSteps(Map<Integer, String> recipeSteps) {
//            this.recipeSteps = recipeSteps;
//            return this;
//        }

        public RecipeModel.Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public RecipeModel.Builder withCalories(Integer calories) {
            this.calories = calories;
            return this;
        }

        public RecipeModel build() {
            return new RecipeModel(userId, recipeName, servings, ingredients, calories);
        }
    }
}
