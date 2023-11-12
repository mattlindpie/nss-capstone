package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import nss.capstone.dynamodb.models.Ingredient;

import java.util.List;
import java.util.Map;

@JsonDeserialize(builder = CreateRecipeRequest.Builder.class)
public class CreateRecipeRequest {

    private final String userId;
    private final String recipeName;
    private final Integer servings;
//    private final Map<Integer, String> recipeSteps;
    private final List<Ingredient> ingredients;
    private final Integer calories;

    public CreateRecipeRequest(String userId, String recipeName, Integer servings, List<Ingredient> ingredients, Integer calories) {
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
//
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
    public String toString() {
        return "CreateRecipeRequest{" +
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
        private Map<Integer, String> recipeSteps;
        private List<Ingredient> ingredients;
        private Integer calories;

        public CreateRecipeRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateRecipeRequest.Builder withRecipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public CreateRecipeRequest.Builder withServings(Integer servings) {
            this.servings = servings;
            return this;
        }
//
//        public CreateRecipeRequest.Builder withRecipeSteps(Map<Integer, String> recipeSteps) {
//            this.recipeSteps = recipeSteps;
//            return this;
//        }
//
        public CreateRecipeRequest.Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public CreateRecipeRequest.Builder withCalories(Integer calories) {
            this.calories = calories;
            return this;
        }

        public CreateRecipeRequest build() {
            return new CreateRecipeRequest(userId, recipeName, servings, ingredients, calories);
        }
    }
}
