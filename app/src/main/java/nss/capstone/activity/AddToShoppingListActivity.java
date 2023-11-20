package nss.capstone.activity;

import nss.capstone.activity.requests.AddToShoppingListRequest;
import nss.capstone.activity.results.AddToShoppingListResult;

import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.ShoppingListModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AddToShoppingListActivity {

    private final ListDao shoppingListDao;

    @Inject
    public AddToShoppingListActivity(ListDao shoppingListDao) {
        this.shoppingListDao = shoppingListDao;
    }

    public AddToShoppingListResult handleRequest(AddToShoppingListRequest request) {
        List<String> ingredients = new ArrayList<>(request.getIngredientNames());
        String userId = request.getUserId();
        ShoppingList shoppingList = shoppingListDao.saveList(ingredients, userId);

        ShoppingListModel shoppingListModel = new ModelConverter().toShoppingListModel(shoppingList);

        return AddToShoppingListResult.builder()
                .withShoppingList(shoppingListModel)
                .build();
    }
}
