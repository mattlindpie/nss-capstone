package nss.capstone.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize
public class GetRecipesByCaloriesRequest {

    private final String userId;
    private final Integer minCalories;
    private final Integer maxCalories;

    private GetRecipesByCaloriesRequest(String userId, Integer minCalories, Integer maxCalories) {
        this.userId = userId;
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getMinCalories() {
        return minCalories;
    }

    public Integer getMaxCalories() {
        return maxCalories;
    }

    @Override
    public String toString() {
        return "GetRecipesByCaloriesRequest{" +
                "userId='" + userId + '\'' +
                ", minCalories=" + minCalories +
                ", maxCalories=" + maxCalories +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private Integer minCalories;
        private Integer maxCalories;

        public GetRecipesByCaloriesRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public GetRecipesByCaloriesRequest.Builder withMinCalories(Integer minCalories) {
            this.minCalories = minCalories;
            return this;
        }
        public GetRecipesByCaloriesRequest.Builder withMaxCalories(Integer maxCalories) {
            this.maxCalories = maxCalories;
            return this;
        }


        public GetRecipesByCaloriesRequest build() {
            return new GetRecipesByCaloriesRequest(userId, minCalories, maxCalories);
        }
    }
}
