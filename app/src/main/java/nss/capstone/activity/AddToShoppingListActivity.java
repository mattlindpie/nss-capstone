package nss.capstone.activity;

import nss.capstone.activity.requests.AddToShoppingListRequest;
import nss.capstone.activity.results.AddToShoppingListResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.ShoppingListModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToShoppingListActivity {

    private final ListDao shoppingListDao;

    @Inject
    public AddToShoppingListActivity(ListDao shoppingListDao) {
        this.shoppingListDao = shoppingListDao;
    }

    public AddToShoppingListResult handleRequest(AddToShoppingListRequest request) {
        List<String> ingredients = new ArrayList<>(request.getIngredientNames());
        Map<String, Integer> shoppingListMap = new HashMap<>();
        ingredients.stream()
                .forEach(ingredient -> shoppingListMap.put(ingredient, 1));

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListItems(shoppingListMap);
        shoppingList.setUserId(request.getUserId());
        shoppingListDao.saveList(shoppingList);

        ShoppingListModel shoppingListModel = new ModelConverter().toShoppingListModel(shoppingList);

        return AddToShoppingListResult.builder()
                .withShoppingList(shoppingListModel)
                .build();
    }
}
