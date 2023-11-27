package nss.capstone.activity.results;

import nss.capstone.models.ShoppingListModel;

public class UpdateShoppingListResult {

    private final ShoppingListModel shoppingListModel;


    public UpdateShoppingListResult(ShoppingListModel shoppingListModel) {
        this.shoppingListModel = shoppingListModel;
    }

    public ShoppingListModel getShoppingListModel() {
        return shoppingListModel;
    }

    @Override
    public String toString() {
        return "UpdateShoppingListResult{" +
                "shoppingListModel=" + shoppingListModel +
                '}';
    }

    //Checkstyle:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ShoppingListModel shoppingListModel;

        public Builder withShoppingListModel(ShoppingListModel shoppingListModel) {
            this.shoppingListModel = shoppingListModel;
            return this;
        }

        public UpdateShoppingListResult build() {
            return new UpdateShoppingListResult(shoppingListModel);
        }
    }
}
