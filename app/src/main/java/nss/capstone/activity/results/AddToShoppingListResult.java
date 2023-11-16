package nss.capstone.activity.results;

import nss.capstone.models.ShoppingListModel;

public class AddToShoppingListResult {

    private final ShoppingListModel shoppingListModel;

    public AddToShoppingListResult(ShoppingListModel shoppingListModel) {
        this.shoppingListModel = shoppingListModel;
    }

    public ShoppingListModel getShoppingListModel() {
        return shoppingListModel;
    }

    @Override
    public String toString() {
        return "AddToShoppingListResult{" +
                "shoppingListModel=" + shoppingListModel +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ShoppingListModel shoppingListModel;

        public Builder withShoppingList(ShoppingListModel shoppingListModel) {
            this.shoppingListModel = shoppingListModel;
            return this;
        }

        public AddToShoppingListResult build() {
            return new AddToShoppingListResult(shoppingListModel);
        }
    }
}
