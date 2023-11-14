package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = DeleteRecipeRequest.Builder.class)
public class DeleteRecipeRequest {

    private final String userId;
    private final String recipeName;

    public DeleteRecipeRequest(String userId, String recipeName) {
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
        return "DeleteRecipeRequest{" +
                "userId='" + userId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String recipeName;

        public DeleteRecipeRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public DeleteRecipeRequest.Builder withRecipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public DeleteRecipeRequest build() {
            return new DeleteRecipeRequest(userId, recipeName);
        }
    }
}
