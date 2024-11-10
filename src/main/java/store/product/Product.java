package store.product;

public abstract class Product {
    private final String name;
    private final int price;
    private int quantity;

    protected Product(final String name, final int price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    protected int decreaseQuantity(final int quantity) {
        int beforeQuantity = this.quantity;
        if (beforeQuantity >= quantity) {
            this.quantity -= quantity;
            return quantity;
        }
        this.quantity = 0;
        return beforeQuantity;
    }
}
