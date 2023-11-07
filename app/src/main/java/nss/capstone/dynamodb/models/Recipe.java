package nss.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "recipes")
public class Recipe {
    private String userId;
    private String recipeName;
    private Integer servings;
    private Map<Integer, String> recipeSteps;
    private List<Ingredient> ingredients;
    private Integer calories;

    @DynamoDBHashKey(attributeName = "userId")
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
    public Map<Integer, String> getRecipeSteps() {
        if(recipeSteps == null) {
            return null;
        }
        return new HashMap<>(recipeSteps);
    }

    public void setRecipeSteps(Map<Integer, String> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

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

    @DynamoDBAttribute(attributeName = "calories")
    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}
