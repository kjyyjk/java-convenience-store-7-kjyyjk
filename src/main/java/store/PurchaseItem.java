package store;

import static store.ErrorMessage.*;

public class PurchaseItem {
    private final String name;
    private final int quantity;

    public PurchaseItem(final String name, final int quantity) {
        validateQuantityAtLeastOne(quantity);
        this.name = name;
        this.quantity = quantity;
    }

    private void validateQuantityAtLeastOne(final int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
