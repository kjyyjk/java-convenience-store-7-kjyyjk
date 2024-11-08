package store.view;

import java.util.Map;
import store.Product;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PRINT_GENERAL_PRODUCT_FORMAT = "- %s %,d원 %s";
    private static final String PRINT_PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s %s";
    private static final String ZERO_QUANTITY = "재고 없음";
    private static final String QUANTITY_UNIT = "개";

    public static void printProducts(final Map<String, Product> products) {
        System.out.println(WELCOME_MESSAGE);
        products.values()
                .forEach(OutputView::printProduct);
    }

    private static void printProduct(final Product product) {
        if (product.hasPromotion()) {
            System.out.println(getPromotionProductMessage(product));
        }
        System.out.println(getGeneralProductMessage(product));
    }

    private static String getPromotionProductMessage(final Product product) {
        String name = product.getName();
        int price = product.getPrice();
        String quantity = quantityToString(product.getPromotionQuantity());
        String promotionName = product.getPromotionName();
        return PRINT_PROMOTION_PRODUCT_FORMAT.formatted(name, price, quantity, promotionName);
    }

    private static String getGeneralProductMessage(final Product product) {
        String name = product.getName();
        int price = product.getPrice();
        String quantity = quantityToString(product.getGeneralQuantity());
        return PRINT_GENERAL_PRODUCT_FORMAT.formatted(name, price, quantity);
    }

    private static String quantityToString(final int quantity) {
        if (quantity == 0) {
            return ZERO_QUANTITY;
        }
        return String.valueOf(quantity) + QUANTITY_UNIT;
    }
}