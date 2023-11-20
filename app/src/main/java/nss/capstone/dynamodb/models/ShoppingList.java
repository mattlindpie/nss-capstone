package nss.capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Map;

@DynamoDBTable(tableName = "shoppingLists")
public class ShoppingList {

    private String userId;
    private Map<String, Integer> shoppingListItems;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "shoppingListItems")
    public Map<String, Integer> getShoppingListItems() {
        return shoppingListItems;
    }

    public void setShoppingListItems(Map<String, Integer> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }


}
