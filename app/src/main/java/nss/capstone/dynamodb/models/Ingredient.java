package nss.capstone.dynamodb.models;

import nss.capstone.utils.UnitOfMeasurement;

import java.util.List;
import java.util.Map;

public class Ingredient {

    private String ingredientName;
    private Double amount;
    private UnitOfMeasurement unitOfMeasurement;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Enum getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }
}
