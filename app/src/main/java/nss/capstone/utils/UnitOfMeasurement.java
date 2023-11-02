package nss.capstone.utils;

public enum UnitOfMeasurement {

    OUNCES("oz"),
    FLUID_OUNCES("fl oz"),
    CUPS("c"),
    TEASPOONS("tsp"),
    TABLESPOONS("tbsp"),
    GRAMS("g"),
    MILLIGRAMS("mg"),
    MILLILITERS("ml"),
    COUNT("x");

    private final String message;


    UnitOfMeasurement(String message) {
        this.message = message;
    }
}
