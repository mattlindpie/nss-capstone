package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.ShoppingListModel;

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
        Map<String, Integer> shoppingListMap = new HashMap<>();
        ingredients.stream()
                .forEach(ingredient -> shoppingListMap.put(ingredient, 1));

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListItems(shoppingListMap);
        shoppingList.setUserId(userId);
        this.mapper.save(shoppingList);

        return shoppingList;
    }

}
