package store.view;

import store.PurchaseHistory;
import store.PurchaseHistoryDetail;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.Products;
import store.product.PromotionProduct;

public class OutputView {
    private static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private static final String PRINT_GENERAL_PRODUCT_FORMAT = "- %s %,d원 %s";
    private static final String PRINT_PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s %s";
    private static final String ZERO_QUANTITY = "재고 없음";
    private static final String QUANTITY_UNIT = "개";
    private static final String PRINT_RECEIPT_HEAD = "\n==============W 편의점================";
    private static final String PRINT_RECEIPT_PURCHASE_HISTORY_DETAIL = "상품명\t\t수량\t금액";
    private static final String PRINT_RECEIPT_PURCHASE_HISTORY_DETAIL_FORMAT = "%s\t\t%d \t%,d";
    private static final String PRINT_RECEIPT_PROMOTION_MESSAGE = "=============증\t정===============";
    private static final String PRINT_RECEIPT_PROMOTION_FORMAT = "%s		%d";
    private static final String PRINT_RECEIPT_PROMOTION_BORDER = "====================================";
    private static final String PRINT_RECEIPT_TOTAL_PURCHASE_AMOUNT_FORMAT = "총구매액\t\t%d\t%,d";
    private static final String PRINT_RECEIPT_PROMOTION_DISCOUNT_AMOUNT_FORMAT = "행사할인\t\t\t-%,d";
    private static final String PRINT_RECEIPT_MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT = "멤버십할인\t\t\t-%,d";
    private static final String PRINT_RECEIPT_PAY_AMOUNT_FORMAT = "내실돈\t\t\t %,d";

    public static void printProducts(final Products products) {
        System.out.println(WELCOME_MESSAGE);
        products.getProducts()
                .forEach(OutputView::printProduct);
    }

    private static void printProduct(final Product product) {
        if (product instanceof PromotionProduct) {
            System.out.println(getPromotionProductMessage((PromotionProduct) product));
        }

        if (product instanceof GeneralProduct) {
            System.out.println(getGeneralProductMessage((GeneralProduct) product));
        }
    }

    private static String getPromotionProductMessage(final PromotionProduct product) {
        String name = product.getName();
        int price = product.getPrice();
        String quantity = quantityToString(product.getQuantity());
        String promotionName = product.getPromotionName();
        return PRINT_PROMOTION_PRODUCT_FORMAT.formatted(name, price, quantity, promotionName);
    }

    private static String getGeneralProductMessage(final GeneralProduct product) {
        String name = product.getName();
        int price = product.getPrice();
        String quantity = quantityToString(product.getQuantity());
        return PRINT_GENERAL_PRODUCT_FORMAT.formatted(name, price, quantity);
    }

    private static String quantityToString(final int quantity) {
        if (quantity == 0) {
            return ZERO_QUANTITY;
        }
        return quantity + QUANTITY_UNIT;
    }

    public static void printReceipt(final PurchaseHistory purchaseHistory) {
        System.out.println(PRINT_RECEIPT_HEAD);
        System.out.println(PRINT_RECEIPT_PURCHASE_HISTORY_DETAIL);
        for (PurchaseHistoryDetail detail : purchaseHistory.getPurchaseHistoryDetails()) {
            System.out.println(PRINT_RECEIPT_PURCHASE_HISTORY_DETAIL_FORMAT.formatted(detail.getProductName(), detail.getTotalQuantity(), detail.getProductPrice()));
        }
        System.out.println(PRINT_RECEIPT_PROMOTION_MESSAGE);
        for (PurchaseHistoryDetail detail : purchaseHistory.getBonusPurchaseHistoryDetails()) {
            System.out.println(PRINT_RECEIPT_PROMOTION_FORMAT.formatted(detail.getProductName(), detail.getBonusQuantity()));
        }
        System.out.println(PRINT_RECEIPT_PROMOTION_BORDER);
        System.out.println(PRINT_RECEIPT_TOTAL_PURCHASE_AMOUNT_FORMAT.formatted(purchaseHistory.calculateTotalPurchaseQuantity(), purchaseHistory.calculateTotalPurchaseAmount()));
        System.out.println(PRINT_RECEIPT_PROMOTION_DISCOUNT_AMOUNT_FORMAT.formatted(purchaseHistory.calculatePromotionDiscountAmount()));
        System.out.println(PRINT_RECEIPT_MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT.formatted(purchaseHistory.calculateMembershipDiscountAmount()));
        System.out.println(PRINT_RECEIPT_PAY_AMOUNT_FORMAT.formatted(purchaseHistory.calculatePayAmount()));
    }

    public static void printError(final IllegalArgumentException e) {
        String message = ERROR_MESSAGE_PREFIX + e.getMessage();
        System.out.println(message);
    }
}