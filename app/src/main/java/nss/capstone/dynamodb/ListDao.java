package nss.capstone.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import nss.capstone.dynamodb.models.ShoppingList;
import nss.capstone.models.ShoppingListModel;

public class ListDao {

    private DynamoDBMapper mapper;

    public ListDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public ShoppingList createList(ShoppingList shoppingList) {
        this.mapper.save(shoppingList);
        return shoppingList;
    }
}
