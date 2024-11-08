package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StoreFileReader {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";

    public static BufferedReader readProducts() throws IOException {
        return new BufferedReader(new FileReader(PRODUCTS_FILE_PATH));
    }
}
