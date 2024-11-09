package store;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StoreFileWriter {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PRODUCTS_FILE_HEAD = "name,price,quantity,promotion";
    private static final String PRODUCTS_FILE_FORMAT = "\n%s,%s,%s,%s";
    private static final String NO_PROMOTION_NAME = "null";

    public static void updateProducts(Map<String, Product> products) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE_PATH));
        writer.write(PRODUCTS_FILE_HEAD);
        for (Product product : products.values()) {
            if (product.hasPromotion()) {
                writer.write(PRODUCTS_FILE_FORMAT.formatted(product.getName(), product.getPrice(), product.getPromotionQuantity(), product.getPromotionName()));
            }

            writer.write(PRODUCTS_FILE_FORMAT.formatted(product.getName(), product.getPrice(), product.getGeneralQuantity(), NO_PROMOTION_NAME));
        }
        writer.flush();
        writer.close();
    }
}
