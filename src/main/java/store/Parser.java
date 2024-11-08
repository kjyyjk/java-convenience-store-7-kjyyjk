package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.PromotionProduct;

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

    public static List<Product> parseProducts(final BufferedReader bufferedReader, Promotions promotions)
            throws IOException {
        List<Product> products = new ArrayList<>();
        String productInformation = bufferedReader.readLine();
        while ((productInformation = bufferedReader.readLine()) != null) {
            Product product = parseProduct(productInformation, promotions);
            products.add(product);
        }
        return Collections.unmodifiableList(products);
    }

    public static List<PurchaseItem> parsePurchaseItems(String input) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (String purchaseItem : input.split(PURCHASE_ITEMS_DELIMITER)) {
            purchaseItems.add(parsePurchaseItem(purchaseItem));
        }
        return purchaseItems;
    }

    private static PurchaseItem parsePurchaseItem(String purchaseItem) {
        validatePurchaseItemFormat(purchaseItem);
        String[] split = getPurchaseItemSubstring(purchaseItem).split(PURCHASE_ITEM_DELIMITER);
        String name = split[0];
        int quantity = parseInt(split[1]);
        return new PurchaseItem(name, quantity);
    }

    private static void validatePurchaseItemFormat(String token) {
        if (!(token.startsWith(PURCHASE_ITEM_PREFIX) && token.endsWith(PURCHASE_ITEM_SUFFIX))) {
            throw new IllegalArgumentException(INVALID_PURCHASE_ITEM_FORMAT);
        }
    }

    private static String getPurchaseItemSubstring(String token) {
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
        return createPromotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }

    private static Promotion createPromotion(final String name, final int buyQuantity, final int bonusQuantity,
                                             final LocalDate startDate,
                                             final LocalDate endDate) {
        return new Promotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }

    private static Product parseProduct(final String productInfo, Promotions promotions) {
        String[] split = productInfo.split(PRODUCT_INFORMATION_DELIMITER);
        Promotion promotion = promotions.get(split[3]);
        return createProduct(split[0], parseInt(split[1]), parseInt(split[2]), promotion);
    }

    private static Product createProduct(final String name, final int price, final int quantity,
                                         final Promotion promotion) {
        if (promotion == null) {
            return new GeneralProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }
}
