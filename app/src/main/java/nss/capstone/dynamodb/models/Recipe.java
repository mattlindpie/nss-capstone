package nss.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import nss.capstone.converters.IngredientConverter;

import java.util.*;

@DynamoDBTable(tableName = "recipes")
public class Recipe {
    public static final String GSI_TABLE_NAME = "RecipesByCaloriesIndex";
    private String userId;
    private String recipeName;
    private Integer servings;
    private List<String> recipeSteps;
    private List<Ingredient> ingredients;
    private Integer calories;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = GSI_TABLE_NAME)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBRangeKey(attributeName = "recipeName")
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @DynamoDBAttribute(attributeName = "servings")
    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    @DynamoDBAttribute(attributeName = "steps")
    public List<String> getRecipeSteps() {
        if(recipeSteps == null) {
            return null;
        }
        return new ArrayList<>(recipeSteps);
    }

    public void setRecipeSteps(List<String> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @DynamoDBTypeConverted(converter = IngredientConverter.class)
    @DynamoDBAttribute(attributeName = "ingredients")
    public List<Ingredient> getIngredients() {
        if(ingredients == null) {
            return null;
        }
        return new ArrayList<>(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = GSI_TABLE_NAME, attributeName = "calories")
    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(userId, recipe.userId)
                && Objects.equals(recipeName, recipe.recipeName)
                && Objects.equals(servings, recipe.servings)
                && Objects.equals(recipeSteps, recipe.recipeSteps)
                && Objects.equals(ingredients, recipe.ingredients)
                && Objects.equals(calories, recipe.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recipeName, recipeSteps, servings, ingredients, calories);
    }
}
