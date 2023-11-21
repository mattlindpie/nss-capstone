package nss.capstone.activity;

import nss.capstone.activity.requests.GetShoppingListRequest;
import nss.capstone.activity.results.GetShoppingListResult;
import nss.capstone.converters.ModelConverter;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;

import javax.inject.Inject;

public class GetShoppingListActivity {

    private ListDao listDao;

    @Inject
    public GetShoppingListActivity(ListDao listDao) {
        this.listDao = listDao;
    }

    public GetShoppingListResult handleRequest(GetShoppingListRequest request) {
        ShoppingList shoppingList = listDao.getShoppingList(request.getUserId());

        return GetShoppingListResult.builder()
                .withShoppingList(new ModelConverter().toShoppingListModel(shoppingList))
                .build();
    }
}
