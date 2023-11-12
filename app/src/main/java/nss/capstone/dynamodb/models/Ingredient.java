package nss.capstone.dynamodb.models;

import nss.capstone.utils.UnitOfMeasurement;

import java.util.List;
import java.util.Map;

public class Ingredient {

    private String ingredientName;
    private Double amount;
    private String unit;

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

    public String getUnitOfMeasurement() {
        return unit;
    }

    public void setUnitOfMeasurement(String unit) {
        this.unit = unit;
    }


}
