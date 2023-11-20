package nss.capstone.models;

import com.amazonaws.services.dynamodbv2.xspec.S;
import nss.capstone.dynamodb.models.Ingredient;
import nss.capstone.dynamodb.models.ShoppingList;

import java.util.Map;
import java.util.Objects;

public class ShoppingListModel {

    private final String userId;
    private final Map<String, Integer> shoppingListItems;

    public ShoppingListModel(String userId, Map<String, Integer> shoppingListItems) {
        this.userId = userId;
        this.shoppingListItems = shoppingListItems;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Integer> getShoppingListItems() {
        return shoppingListItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingListModel that = (ShoppingListModel) o;
        return Objects.equals(userId, that.userId) && Objects.equals(shoppingListItems, that.shoppingListItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, shoppingListItems);
    }

    @Override
    public String toString() {
        return "ShoppingListModel{" +
                "userId=" + userId + '\'' +
                ", shoppingListItems=" + shoppingListItems +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private Map<String, Integer> shoppingListItems;

        public ShoppingListModel.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public ShoppingListModel.Builder withShoppingListItems(Map<String, Integer> shoppingListItems) {
            this.shoppingListItems = shoppingListItems;
            return this;
        }

        public ShoppingListModel build() {
            return new ShoppingListModel(userId, shoppingListItems);
        }
    }

}
