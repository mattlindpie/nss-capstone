package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetShoppingListRequest.Builder.class)
public class GetShoppingListRequest {

    private final String userId;

    public GetShoppingListRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetShoppingListRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String userId;

        public GetShoppingListRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetShoppingListRequest build() {
            return new GetShoppingListRequest(userId);
        }
    }
}
