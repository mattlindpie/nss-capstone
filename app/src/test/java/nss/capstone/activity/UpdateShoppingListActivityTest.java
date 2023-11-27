package nss.capstone.activity;

import nss.capstone.activity.requests.UpdateShoppingListRequest;
import nss.capstone.activity.results.UpdateShoppingListResult;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateShoppingListActivityTest {

    @Mock
    ListDao listDao;

    UpdateShoppingListActivity updateShoppingListActivity;

    @BeforeEach
    void setup() {
        openMocks(this);

        updateShoppingListActivity = new UpdateShoppingListActivity(listDao);
    }

    @Test
    public void handleRequest_changeQuantity_reflectsChanges() {
        String userId = "userId";

        Map<String, Integer> shoppingListMap = new HashMap<>();
        shoppingListMap.put("lemon", 1);
        shoppingListMap.put("cinnamon", 1);


        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setUserId(userId);
        shoppingList.setShoppingListItems(shoppingListMap);

        Map<String, Integer> newQuantities = new HashMap<>();
        newQuantities.put("lemon", 3);
        newQuantities.put("cinnamon", 2);

        UpdateShoppingListRequest request = UpdateShoppingListRequest.builder()
                .withUserId(userId)
                .withShoppingListItems(newQuantities)
                .build();

        when(listDao.getShoppingList(userId)).thenReturn(shoppingList);
        when(listDao.updateShoppingList(shoppingList)).thenReturn(shoppingList);
        UpdateShoppingListResult result = updateShoppingListActivity.handleRequest(request);

        assertTrue(result.getShoppingListModel().getShoppingListItems().get("lemon") == 3);
        assertTrue(result.getShoppingListModel().getShoppingListItems().get("cinnamon") == 2);

    }

}