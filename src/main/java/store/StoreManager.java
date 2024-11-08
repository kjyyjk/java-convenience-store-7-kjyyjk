package store;

import static store.StoreFileReader.readProducts;
import static store.Parser.parseProducts;

import java.io.IOException;
import java.util.List;
import store.product.Product;

public class StoreManager {
    public void run() {
        List<Product> products = getProducts();
    }

    private static List<Product> getProducts() {
        try {
            return parseProducts(readProducts());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
