package store;

public class PurchaseItem {
    private final String name;
    private final int quantity;

    public PurchaseItem(final String name, final int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
