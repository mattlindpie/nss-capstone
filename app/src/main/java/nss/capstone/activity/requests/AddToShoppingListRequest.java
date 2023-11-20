package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = AddToShoppingListRequest.Builder.class)
public class AddToShoppingListRequest {
    private final String userId;
    private final List<String> ingredientNames;

    public AddToShoppingListRequest(String userId, List<String> ingredientNames) {
        this.userId = userId;
        this.ingredientNames = ingredientNames;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    @Override
    public String toString() {
        return "AddToShoppingListRequest{" +
                "userId='" + userId + '\'' +
                ", ingredientNames=" + ingredientNames +
                '}';
    }

    //Checkstyle:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private List<String> ingredientNames;

        public AddToShoppingListRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public AddToShoppingListRequest.Builder withIngredientNames(List<String> ingredientNames) {
            this.ingredientNames = ingredientNames;
            return this;
        }

        public AddToShoppingListRequest build() {
            return new AddToShoppingListRequest(userId, ingredientNames);
        }
    }
}
