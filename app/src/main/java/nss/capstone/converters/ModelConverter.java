package nss.capstone.converters;

import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.RecipeModel;
import nss.capstone.models.ShoppingListModel;

public class ModelConverter {

    public RecipeModel toRecipeModel(Recipe recipe) {
        return RecipeModel.builder()
                .withUserId(recipe.getUserId())
                .withRecipeName(recipe.getRecipeName())
                .withServings(recipe.getServings())
                .withRecipeSteps(recipe.getRecipeSteps())
                .withIngredients(recipe.getIngredients())
                .withCalories(recipe.getCalories())
                .build();
    }

    public ShoppingListModel toShoppingListModel(ShoppingList shoppingList) {
        return ShoppingListModel.builder()
                .withUserId(shoppingList.getUserId())
                .withShoppingListItems(shoppingList.getShoppingListItems())
                .build();
    }
}
