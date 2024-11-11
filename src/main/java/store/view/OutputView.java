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
    private static final String RECEIPT_HEAD = "\n==============W 편의점================";
    private static final String RECEIPT_PURCHASE_HISTORY_DETAIL = "상품명           수량           금액";
    private static final String RECEIPT_PURCHASE_HISTORY_DETAIL_FORMAT = "%-13s%3d            %,-6d";
    private static final String RECEIPT_PROMOTION_MESSAGE = "=============증\t\t정===============";
    private static final String RECEIPT_PROMOTION_FORMAT = "%-13s%3d";
    private static final String RECEIPT_PROMOTION_BORDER = "====================================";
    private static final String RECEIPT_TOTAL_PURCHASE_AMOUNT_FORMAT = "총구매액%12d            %,-6d";
    private static final String RECEIPT_PROMOTION_DISCOUNT_AMOUNT_FORMAT = "행사할인                        -%,-6d";
    private static final String RECEIPT_MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT = "멤버십할인                       -%,-6d";
    private static final String RECEIPT_PAY_AMOUNT_FORMAT = "내실돈                          %,-6d";

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
        System.out.println(RECEIPT_HEAD);
        pringPurchaseHistory(purchaseHistory);
        printReceiptPromotion(purchaseHistory);
        printTotalPurchaseAmount(purchaseHistory);
        printDiscountAmount(purchaseHistory);
        printPayAmount(purchaseHistory);
    }

    private static void printPayAmount(final PurchaseHistory purchaseHistory) {
        int payAmount = purchaseHistory.calculatePayAmount();
        System.out.println(RECEIPT_PAY_AMOUNT_FORMAT.formatted(payAmount));
    }

    private static void printDiscountAmount(final PurchaseHistory purchaseHistory) {
        int promotionDiscountAmount = purchaseHistory.calculatePromotionDiscountAmount();
        int membershipDiscountAmount = purchaseHistory.calculateMembershipDiscountAmount();
        System.out.println(RECEIPT_PROMOTION_DISCOUNT_AMOUNT_FORMAT.formatted(promotionDiscountAmount));
        System.out.println(RECEIPT_MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT.formatted(membershipDiscountAmount));
    }

    private static void printTotalPurchaseAmount(final PurchaseHistory purchaseHistory) {
        int totalPurchaseQuantity = purchaseHistory.calculateTotalPurchaseQuantity();
        int totalPurchaseAmount = purchaseHistory.calculateTotalPurchaseAmount();
        System.out.println(RECEIPT_TOTAL_PURCHASE_AMOUNT_FORMAT.formatted(totalPurchaseQuantity, totalPurchaseAmount));
    }

    private static void pringPurchaseHistory(final PurchaseHistory purchaseHistory) {
        System.out.println(RECEIPT_PURCHASE_HISTORY_DETAIL);
        for (PurchaseHistoryDetail detail : purchaseHistory.getPurchaseHistoryDetails()) {
            System.out.println(RECEIPT_PURCHASE_HISTORY_DETAIL_FORMAT.formatted(detail.getProductName(),
                    detail.getTotalQuantity(), detail.getProductPrice()));
        }
    }

    private static void printReceiptPromotion(final PurchaseHistory purchaseHistory) {
        System.out.println(RECEIPT_PROMOTION_MESSAGE);
        for (PurchaseHistoryDetail detail : purchaseHistory.getBonusPurchaseHistoryDetails()) {
            System.out.println(
                    RECEIPT_PROMOTION_FORMAT.formatted(detail.getProductName(), detail.getBonusQuantity()));
        }
        System.out.println(RECEIPT_PROMOTION_BORDER);
    }

    public static void printError(final IllegalArgumentException e) {
        String message = ERROR_MESSAGE_PREFIX + e.getMessage();
        System.out.println(message);
    }
}