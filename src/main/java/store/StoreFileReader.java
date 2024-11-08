package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StoreFileReader {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    public static BufferedReader readProducts() throws IOException {
        return new BufferedReader(new FileReader(PRODUCTS_FILE_PATH));
    }

    public static BufferedReader readPromotions() throws IOException {
        return new BufferedReader(new FileReader(PROMOTION_FILE_PATH));
    }
}
