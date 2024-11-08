package store.view;

import java.util.List;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.PromotionProduct;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PRINT_GENERAL_PRODUCT_FORMAT = "- %s %,d원 %s개";
    private static final String PRINT_PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s개 %s";
    private static final String ZERO_QUANTITY = "재고 없음";

    public static void printProducts(final List<Product> products) {
        System.out.println(WELCOME_MESSAGE);
        products.forEach(OutputView::printProduct);
    }

    private static void printProduct(final Product product) {
        if (product instanceof GeneralProduct) {
            String message = getGeneralProductPrintMessage((GeneralProduct) product);
            System.out.println(message);
        }
        if (product instanceof PromotionProduct) {
            String message = getPromotionProductPrintMessage((PromotionProduct) product);
            System.out.println(message);
        }
    }

    private static String getPromotionProductPrintMessage(final PromotionProduct promotionProduct) {
        String name = promotionProduct.getName();
        int price = promotionProduct.getPrice();
        String quantity = quantityToString(promotionProduct.getQuantity());
        String promotionName = promotionProduct.getPromotionName();
        return PRINT_PROMOTION_PRODUCT_FORMAT.formatted(name, price, quantity, promotionName);
    }

    private static String getGeneralProductPrintMessage(final GeneralProduct generalProduct) {
        String name = generalProduct.getName();
        int price = generalProduct.getPrice();
        String quantity = quantityToString(generalProduct.getQuantity());
        return PRINT_GENERAL_PRODUCT_FORMAT.formatted(name, price, quantity);
    }

    private static String quantityToString(final int quantity) {
        if (quantity == 0) {
            return ZERO_QUANTITY;
        }
        return String.valueOf(quantity);
    }
}