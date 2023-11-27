package nss.capstone.activity;

import nss.capstone.activity.requests.UpdateShoppingListRequest;
import nss.capstone.activity.results.UpdateShoppingListResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;

import javax.inject.Inject;

public class UpdateShoppingListActivity {

    private final ListDao listDao;

    @Inject
    public UpdateShoppingListActivity(ListDao listDao) {
        this.listDao = listDao;
    }

    public UpdateShoppingListResult handleRequest(UpdateShoppingListRequest request) {

        ShoppingList shoppingList = listDao.getShoppingList(request.getUserId());

        shoppingList.setShoppingListItems(request.getShoppingListItems());

        shoppingList = listDao.updateShoppingList(shoppingList);

        return UpdateShoppingListResult.builder()
                .withShoppingListModel(new ModelConverter().toShoppingListModel(shoppingList))
                .build();
    }
}
