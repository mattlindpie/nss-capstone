package nss.capstone.activity.results;

import nss.capstone.models.ShoppingListModel;

import java.util.Objects;

public class GetShoppingListResult {

    private final ShoppingListModel shoppingList;

    public GetShoppingListResult(ShoppingListModel shoppingList) {
        this.shoppingList = shoppingList;
    }

    public ShoppingListModel getShoppingList() {
        return shoppingList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ShoppingListModel shoppingList;

        public GetShoppingListResult.Builder withShoppingList(ShoppingListModel shoppingList) {
            this.shoppingList = shoppingList;
            return this;
        }

        public GetShoppingListResult build() {
            return new GetShoppingListResult(shoppingList);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(shoppingList, builder.shoppingList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(shoppingList);
        }
    }
}
