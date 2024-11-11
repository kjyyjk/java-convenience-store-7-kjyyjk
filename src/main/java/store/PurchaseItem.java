package store;

import static store.ErrorMessage.INVALID_INPUT;

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

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof PurchaseItem)) {
            return false;
        }
        String name = ((PurchaseItem) obj).getName();
        return this.name.equals(name);
    }
}
