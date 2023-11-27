package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Map;

@JsonDeserialize(builder = UpdateShoppingListRequest.Builder.class)
public class UpdateShoppingListRequest {

    private final String userId;
    private final Map<String, Integer> shoppingListItems;

    public UpdateShoppingListRequest(String userId, Map<String, Integer> shoppingListItems) {
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
    public String toString() {
        return "UpdateShoppingListRequest{" +
                "userId='" + userId + '\'' +
                ", shoppingListItems=" + shoppingListItems +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Map<String, Integer> shoppingListItems;

        public UpdateShoppingListRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UpdateShoppingListRequest.Builder withShoppingListItems(Map<String, Integer> shoppingListItems) {
            this.shoppingListItems = shoppingListItems;
            return this;
        }

        public UpdateShoppingListRequest build() {
            return new UpdateShoppingListRequest(userId, shoppingListItems);
        }
    }
}
