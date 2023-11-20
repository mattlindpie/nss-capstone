package nss.capstone.activity;

import nss.capstone.activity.requests.AddToShoppingListRequest;
import nss.capstone.activity.results.AddToShoppingListResult;
import nss.capstone.dynamodb.ListDao;
import nss.capstone.dynamodb.models.ShoppingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AddToShoppingListActivityTest {

    @Mock
    ListDao listDao;

    AddToShoppingListActivity listActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        listActivity = new AddToShoppingListActivity(listDao);
    }

    @Test
    public void handleRequest_addsNewItems() {
        String userId = "userId";
        List<String> newIngredients = new ArrayList<>(Arrays.asList("lemon", "cinnamon"));

        Map<String, Integer> shoppingListMap = new HashMap<>();
        shoppingListMap.put("lemon", 1);
        shoppingListMap.put("cinnamon", 1);


        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setUserId(userId);
        shoppingList.setShoppingListItems(shoppingListMap);

        AddToShoppingListRequest request = AddToShoppingListRequest.builder()
                .withIngredientNames(newIngredients)
                .withUserId(userId)
                .build();

        when(listDao.saveList(newIngredients, userId)).thenReturn(shoppingList);
        AddToShoppingListResult result = listActivity.handleRequest(request);

        assertTrue(result.getShoppingListModel().getShoppingListItems().containsKey("lemon"));
        assertTrue(result.getShoppingListModel().getShoppingListItems().containsKey("cinnamon"));
        verify(listDao).saveList(anyList(), anyString());

    }

}