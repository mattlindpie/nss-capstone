package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize
public class GetAllRecipesRequest {

    private String userId;
    private String recipeName;

    private GetAllRecipesRequest(String userId, String recipeName) {
        this.userId = userId;
        this.recipeName = recipeName;
    }

    public String getUserId() {
        return userId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public String toString() {
        return "GetAllRecipesRequest{" +
                "userId='" + userId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                '}';
    }

    public static GetAllRecipesRequest.Builder builder() {
        return new GetAllRecipesRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String recipeName;

        public GetAllRecipesRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetAllRecipesRequest.Builder withRecipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public GetAllRecipesRequest build() {
            return new GetAllRecipesRequest(userId, recipeName);
        }
    }
}
