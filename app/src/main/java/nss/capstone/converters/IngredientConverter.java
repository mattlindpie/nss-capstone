package nss.capstone.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nss.capstone.dynamodb.models.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientConverter implements DynamoDBTypeConverter<String, List<Ingredient>> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<Ingredient> ingredients) {
        try {
            return objectMapper.writeValueAsString(ingredients);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> unconvert(String ingredientString) {
        try {
            return objectMapper.readValue(ingredientString, new TypeReference<List<Ingredient>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
