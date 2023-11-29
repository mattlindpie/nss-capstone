package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import nss.capstone.dynamodb.models.Ingredient;

import java.util.List;

@JsonDeserialize(builder = UpdateRecipeRequest.Builder.class)
public class UpdateRecipeRequest {

    private final String userId;
    private final String recipeName;
    private final Integer servings;
    private final List<String> recipeSteps;
    private final List<Ingredient> ingredients;
    private final Integer calories;

    public UpdateRecipeRequest(String userId, String recipeName, Integer servings, List<String> recipeSteps, List<Ingredient> ingredients, Integer calories) {
        this.userId = userId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.recipeSteps = recipeSteps;
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

    public List<String> getRecipeSteps() {
        return recipeSteps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Integer getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "UpdateRecipeRequest{" +
                "userId='" + userId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", servings=" + servings +
                ", ingredients=" + ingredients +
                ", calories=" + calories +
                '}';
    }

    //Checkstyle:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String userId;
        private String recipeName;
        private Integer servings;
        private List<String> recipeSteps;
        private List<Ingredient> ingredients;
        private Integer calories;

        public UpdateRecipeRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UpdateRecipeRequest.Builder withRecipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public UpdateRecipeRequest.Builder withServings(Integer servings) {
            this.servings = servings;
            return this;
        }

        public UpdateRecipeRequest.Builder withRecipeSteps(List<String> recipeSteps) {
            this.recipeSteps = recipeSteps;
            return this;
        }

        public UpdateRecipeRequest.Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public UpdateRecipeRequest.Builder withCalories(Integer calories) {
            this.calories = calories;
            return this;
        }

        public UpdateRecipeRequest build() {
            return new UpdateRecipeRequest(userId, recipeName, servings, recipeSteps, ingredients, calories);
        }
    }

}
