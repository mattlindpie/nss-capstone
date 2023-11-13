package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize
public class GetAllRecipesRequest {

    private String userId;
    private GetAllRecipesRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return "GetAllRecipesRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public static GetAllRecipesRequest.Builder builder() {
        return new GetAllRecipesRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GetAllRecipesRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }


        public GetAllRecipesRequest build() {
            return new GetAllRecipesRequest(userId);
        }
    }
}
