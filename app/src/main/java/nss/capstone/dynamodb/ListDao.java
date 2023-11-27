package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.ShoppingList;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDao {

    private DynamoDBMapper mapper;

    @Inject
    public ListDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public ShoppingList saveList(List<String> ingredients, String userId) {
        try {
            ShoppingList shoppingList = getShoppingList(userId);
            Map<String, Integer> shoppingListMap = shoppingList.getShoppingListItems();

            for (String ingredient : ingredients) {
                if (shoppingListMap.containsKey(ingredient)) {
                    shoppingListMap.put(ingredient, shoppingListMap.get(ingredient) + 1);
                } else {
                    shoppingListMap.put(ingredient, 1);
                }
            }
            shoppingList.setShoppingListItems(shoppingListMap);
            this.mapper.save(shoppingList);
            return shoppingList;
        } catch (NullPointerException e) {
            ShoppingList newShoppingList = new ShoppingList();
            Map<String, Integer> shoppingListMap = new HashMap<>();
            ingredients.stream()
                    .forEach(ingredient -> shoppingListMap.put(ingredient, 1));
            newShoppingList.setShoppingListItems(shoppingListMap);
            newShoppingList.setUserId(userId);
            this.mapper.save(newShoppingList);
            return newShoppingList;
        }
    }

    public ShoppingList getShoppingList(String userId) {
        return mapper.load(ShoppingList.class, userId);
    }

    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        this.mapper.save(shoppingList);
        return shoppingList;
    }

}
