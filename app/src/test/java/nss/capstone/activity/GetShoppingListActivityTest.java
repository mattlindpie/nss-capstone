package nss.capstone.activity;

import nss.capstone.activity.requests.GetShoppingListRequest;
import nss.capstone.activity.results.GetShoppingListResult;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class GetShoppingListActivityTest {

    @Mock
    ListDao listDao;

    GetShoppingListActivity listActivity;

    @BeforeEach
    void setup() {
        openMocks(this);

        listActivity = new GetShoppingListActivity(listDao);
    }

    @Test
    public void handleRequest_() {
        ShoppingList shoppingList = new ShoppingList();
        String userId = "userId";
        Map<String, Integer> shoppingListMap = new HashMap<>();
        shoppingListMap.put("lemon", 1);
        shoppingListMap.put("cinnamon", 1);

        shoppingList.setShoppingListItems(shoppingListMap);
        shoppingList.setUserId(userId);

        when(listDao.getShoppingList(anyString())).thenReturn(shoppingList);

        GetShoppingListRequest request = GetShoppingListRequest.builder()
                .withUserId("userId")
                .build();

        GetShoppingListResult result = listActivity.handleRequest(request);

        assertEquals(shoppingList.getUserId(), result.getShoppingList().getUserId());
        assertTrue(result.getShoppingList().getShoppingListItems().containsKey("lemon"));
        assertTrue(result.getShoppingList().getShoppingListItems().containsKey("cinnamon"));
    }

}