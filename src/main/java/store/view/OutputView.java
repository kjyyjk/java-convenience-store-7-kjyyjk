package store.view;

import static store.Receipt.HEADER;
import static store.Receipt.MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT;
import static store.Receipt.PAY_AMOUNT_FORMAT;
import static store.Receipt.PROMOTION_FOOTER;
import static store.Receipt.PROMOTION_DISCOUNT_AMOUNT_FORMAT;
import static store.Receipt.PROMOTION_FORMAT;
import static store.Receipt.PROMOTION_HEADER;
import static store.Receipt.PURCHASE_HISTORY_DETAIL_FOOTER;
import static store.Receipt.PURCHASE_HISTORY_DETAIL_FORMAT;
import static store.Receipt.TOTAL_PURCHASE_AMOUNT_FORMAT;

import store.ErrorMessage;
import store.PurchaseHistory;
import store.PurchaseHistoryDetail;
import store.product.GeneralProduct;
import store.product.Product;
import store.product.Products;
import store.product.PromotionProduct;

public class OutputView {
    private static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    private static final String PRINT_GENERAL_PRODUCT_FORMAT = "- %s %,d원 %s";
    private static final String PRINT_PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s %s";
    private static final String ZERO_QUANTITY = "재고 없음";
    private static final String QUANTITY_UNIT = "개";

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
        System.out.println(HEADER.getMessage());
        pringPurchaseHistory(purchaseHistory);
        printReceiptPromotion(purchaseHistory);
        printTotalPurchaseAmount(purchaseHistory);
        printDiscountAmount(purchaseHistory);
        printPayAmount(purchaseHistory);
    }

    private static void printPayAmount(final PurchaseHistory purchaseHistory) {
        int payAmount = purchaseHistory.calculatePayAmount();
        String payAmountFormat = PAY_AMOUNT_FORMAT.getMessage();
        System.out.println(payAmountFormat.formatted(payAmount));
    }

    private static void printDiscountAmount(final PurchaseHistory purchaseHistory) {
        int promotionDiscountAmount = purchaseHistory.calculatePromotionDiscountAmount();
        int membershipDiscountAmount = purchaseHistory.calculateMembershipDiscountAmount();
        String promotionDiscountAmountFormat = PROMOTION_DISCOUNT_AMOUNT_FORMAT.getMessage();
        String membershipDiscountAmountFormat = MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT.getMessage();
        System.out.println(promotionDiscountAmountFormat.formatted(promotionDiscountAmount));
        System.out.println(membershipDiscountAmountFormat.formatted(membershipDiscountAmount));
    }

    private static void printTotalPurchaseAmount(final PurchaseHistory purchaseHistory) {
        int totalPurchaseQuantity = purchaseHistory.calculateTotalPurchaseQuantity();
        int totalPurchaseAmount = purchaseHistory.calculateTotalPurchaseAmount();
        String totalPurchaseAmountFormat = TOTAL_PURCHASE_AMOUNT_FORMAT.getMessage();
        System.out.println(totalPurchaseAmountFormat.formatted(totalPurchaseQuantity, totalPurchaseAmount));
    }

    private static void pringPurchaseHistory(final PurchaseHistory purchaseHistory) {
        System.out.println(PURCHASE_HISTORY_DETAIL_FOOTER);
        for (PurchaseHistoryDetail detail : purchaseHistory.getPurchaseHistoryDetails()) {
            String purchaseHistoryDetailFormat = PURCHASE_HISTORY_DETAIL_FORMAT.getMessage();
            System.out.println(purchaseHistoryDetailFormat.formatted(detail.getProductName(),
                    detail.getTotalQuantity(), detail.getProductPrice()));
        }
    }

    private static void printReceiptPromotion(final PurchaseHistory purchaseHistory) {
        System.out.println(PROMOTION_HEADER);
        for (PurchaseHistoryDetail detail : purchaseHistory.getBonusPurchaseHistoryDetails()) {
            String promotionFormat = PROMOTION_FORMAT.getMessage();
            System.out.println(promotionFormat.formatted(detail.getProductName(), detail.getBonusQuantity()));
        }
        System.out.println(PROMOTION_FOOTER.getMessage());
    }

    public static void printError(final IllegalArgumentException e) {
        String errorMessagePrefix = ErrorMessage.PREFIX
                .getMessage();
        String message = errorMessagePrefix + e.getMessage();
        System.out.println(message);
    }
}