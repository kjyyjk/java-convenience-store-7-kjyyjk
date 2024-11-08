package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.PromotionProduct;

public class Parser {
    private static final String PRODUCT_INFORMATION_DELIMITER = ",";
    private static final String NO_PROMOTION = "null";

    public static List<Product> parseProducts(final BufferedReader bufferedReader) throws IOException {
        List<Product> products = new ArrayList<>();
        String productInformation;
        bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            Product product = parseProduct(productInformation);
            products.add(product);
        }
        return products;
    }

    private static Product parseProduct(final String productInfo) {
        String[] split = productInfo.split(PRODUCT_INFORMATION_DELIMITER);
        return createProduct(split[0], parseInt(split[1]), parseInt(split[2]), split[3]);
    }

    private static Product createProduct(final String name, final int price, final int quantity,
                                         final String promotion) {
        if (promotion.equals(NO_PROMOTION)) {
            return new GeneralProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }

    public static int parseInt(final String number) {
        return Integer.parseInt(number);
    }
}
