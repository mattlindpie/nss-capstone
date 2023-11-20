package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.ShoppingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ListDaoTest {

    @Mock
    private DynamoDBMapper mapper;

    private ListDao listDao;
    private final String USER_ID = "userId";

    @BeforeEach
    void setup() {
        openMocks(this);
        listDao = new ListDao(mapper);
    }

    @Test
    public void saveList_listExists_savesNewItems() {
//        Map<String, Integer> shoppingListMap = new HashMap<>();
//        shoppingListMap.put("honey", 1);
//        shoppingListMap.put("black tea", 1);
//
//        List<String> newIngredients = new ArrayList<>(Arrays.asList("lemon", "cinnamon"));
//
//        ShoppingList shoppingList = new ShoppingList();
//        shoppingList.setUserId(USER_ID);
//        shoppingList.setShoppingListItems(shoppingListMap);
//
//        when(mapper.load(any())).thenReturn(shoppingList);
//
//        listDao.saveList(newIngredients, USER_ID);
//        assertEquals(4, shoppingList.getShoppingListItems().size());
//        verify(mapper).save(any());
    }

    @Test
    public void saveList_listDoesNotExist_createsNewList() {
        when(mapper.load(any())).thenReturn(null);

        List<String> newIngredients = new ArrayList<>(Arrays.asList("lemon", "cinnamon"));

        ShoppingList result = listDao.saveList(newIngredients, USER_ID);
        assertEquals(2, result.getShoppingListItems().size());
        verify(mapper).save(any());
    }

}