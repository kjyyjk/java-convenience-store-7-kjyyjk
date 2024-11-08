package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final String PRODUCT_INFORMATION_DELIMITER = ",";
    private static final String PROMOTION_INFORMATION_DELIMITER = ",";
    private static final String PURCHASE_ITEMS_DELIMITER = ",";
    private static final String PURCHASE_ITEM_DELIMITER = "-";
    private static final String PURCHASE_ITEM_PREFIX = "[";
    private static final String PURCHASE_ITEM_SUFFIX = "]";
    private static final String INVALID_PURCHASE_ITEM_FORMAT = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";

    public static Map<String, Promotion> parsePromotions(final BufferedReader bufferedReader) throws IOException {
        Map<String, Promotion> promotions = new HashMap<>();
        String promotionInformation = bufferedReader.readLine();
        while ((promotionInformation = bufferedReader.readLine()) != null) {
            Promotion promotion = parsePromotion(promotionInformation);
            promotions.put(promotion.getName(), promotion);
        }
        return Collections.unmodifiableMap(promotions);
    }

    public static Map<String, Product> parseProducts(final BufferedReader bufferedReader, final Promotions promotions)
            throws IOException {
        Map<String, Product> products = new HashMap<>();
        String productInformation = bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            String[] split = productInformation.split(PRODUCT_INFORMATION_DELIMITER);
            Product product = getProduct(promotions, products, split);
            products.put(product.getName(), product);
        }
        return Collections.unmodifiableMap(products);
    }

    private static Product getProduct(Promotions promotions, Map<String, Product> products, String[] split) {
        String name = split[0];
        int price = parseInt(split[1]);
        int quantity = parseInt(split[2]);
        Promotion promotion = promotions.get(split[3]);
        Product product = getProduct(products, name, price);
        increaseProductQuantity(product, promotion, quantity);
        return product;
    }

    private static Product getProduct(Map<String, Product> products, String name, int price) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        return new Product(name, price);
    }

    private static void increaseProductQuantity(Product product, Promotion promotion, int quantity) {
        if (promotion == null) {
            product.increaseGeneralQuantity(quantity);
            return;
        }

        product.doPromotion(promotion);
        product.increasePromotionQuantity(quantity);
    }

    public static List<PurchaseItem> parsePurchaseItems(final String input) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (String purchaseItem : input.split(PURCHASE_ITEMS_DELIMITER)) {
            purchaseItems.add(parsePurchaseItem(purchaseItem));
        }
        return purchaseItems;
    }

    private static PurchaseItem parsePurchaseItem(final String purchaseItem) {
        validatePurchaseItemFormat(purchaseItem);
        String[] split = getPurchaseItemSubstring(purchaseItem).split(PURCHASE_ITEM_DELIMITER);
        String name = split[0];
        int quantity = parseInt(split[1]);
        return new PurchaseItem(name, quantity);
    }

    private static void validatePurchaseItemFormat(final String token) {
        if (!(token.startsWith(PURCHASE_ITEM_PREFIX) && token.endsWith(PURCHASE_ITEM_SUFFIX))) {
            throw new IllegalArgumentException(INVALID_PURCHASE_ITEM_FORMAT);
        }
    }

    private static String getPurchaseItemSubstring(final String token) {
        int beginIndex = PURCHASE_ITEM_PREFIX.length();
        int endIndex = token.length() - PURCHASE_ITEM_SUFFIX.length();
        return token.substring(beginIndex, endIndex);
    }

    public static int parseInt(final String number) {
        return Integer.parseInt(number);
    }

    public static LocalDate parseLocalDate(final String date) {
        return LocalDate.parse(date);
    }

    private static Promotion parsePromotion(final String promotionInformation) {
        String[] split = promotionInformation.split(PROMOTION_INFORMATION_DELIMITER);
        String name = split[0];
        int buyQuantity = parseInt(split[1]);
        int bonusQuantity = parseInt(split[2]);
        LocalDate startDate = parseLocalDate(split[3]);
        LocalDate endDate = parseLocalDate(split[4]);
        return new Promotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }
}
