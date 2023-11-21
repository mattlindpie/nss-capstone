package nss.capstone.converters;

import nss.capstone.dynamodb.models.Recipe;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.RecipeModel;
import nss.capstone.models.ShoppingListModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    public void toRecipeModel_withStepsAndIngredients_convertsRecipe() {
        Recipe recipe = new Recipe();
        recipe.setUserId("validId");
        recipe.setRecipeName("validName");
        recipe.setIngredients(new ArrayList<>());
        recipe.setRecipeSteps(new ArrayList<>());
        recipe.setServings(4);
        recipe.setCalories(100);

        RecipeModel result = modelConverter.toRecipeModel(recipe);
        assertEquals(recipe.getUserId(), result.getUserId());
        assertEquals(recipe.getRecipeName(), result.getRecipeName());
        assertEquals(recipe.getIngredients(), result.getIngredients());
        assertEquals(recipe.getRecipeSteps(), result.getRecipeSteps());
        assertEquals(recipe.getServings(), result.getServings());
        assertEquals(recipe.getCalories(), result.getCalories());

    }

    @Test
    public void toListModel_convertsList() {
        Map<String, Integer> shoppingListMap = new HashMap<>();
        shoppingListMap.put("lemon", 1);
        shoppingListMap.put("cinnamon", 1);

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListItems(shoppingListMap);
        shoppingList.setUserId("userId");

        ShoppingListModel result = modelConverter.toShoppingListModel(shoppingList);
        assertEquals(shoppingList.getUserId(), result.getUserId());
        assertEquals(shoppingList.getShoppingListItems(), result.getShoppingListItems());
    }
}