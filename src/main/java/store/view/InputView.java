package store.view;

import static store.InputMessage.READ_ADDITIONAL_PURCHASE_MESSAGE;
import static store.InputMessage.READ_EXTRA_PROMOTION_QUANTITY_MESSAGE;
import static store.InputMessage.READ_MEMBERSHIP_DISCOUNT_MESSAGE;
import static store.InputMessage.READ_NO_PROMOTION_QUANTITY_MESSAGE;
import static store.InputMessage.READ_PURCHASE_ITEM_MESSAGE;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public static String readPurchaseItems() {
        System.out.println(READ_PURCHASE_ITEM_MESSAGE.getMessage());
        return input();
    }

    public static String readPurchaseExtraPromotionQuantity(final String productName, final int extraBonusQuantity) {
        String message = READ_EXTRA_PROMOTION_QUANTITY_MESSAGE.getMessage()
                .formatted(productName, extraBonusQuantity);
        System.out.println(message);
        return input();
    }

    public static String input() {
        return Console.readLine();
    }

    public static String readPurchasePromotionNotAppliedQuantity(final String productName, final int promotionNotAppliedQuantity) {
        String message = READ_NO_PROMOTION_QUANTITY_MESSAGE.getMessage()
                .formatted(productName, promotionNotAppliedQuantity);
        System.out.println(message);
        return input();
    }

    public static String readGetMembershipDiscount() {
        System.out.println(READ_MEMBERSHIP_DISCOUNT_MESSAGE.getMessage());
        return input();
    }

    public static String readAdditionalPurchase() {
        System.out.println(READ_ADDITIONAL_PURCHASE_MESSAGE.getMessage());
        return input();
    }
}