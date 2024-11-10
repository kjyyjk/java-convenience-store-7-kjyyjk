package store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InputParser {
    private static final String PURCHASE_ITEMS_DELIMITER = ",";
    private static final String PURCHASE_ITEM_DELIMITER = "-";
    private static final String PURCHASE_ITEM_PREFIX = "[";
    private static final String PURCHASE_ITEM_SUFFIX = "]";
    private static final String INVALID_PURCHASE_ITEM_FORMAT = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final String INVALID_INPUT_ERROR_MESSAGE = "잘못된 입력입니다. 다시 입력해 주세요.";

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
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
        }
    }

    public static LocalDate parseLocalDate(final String date) {
        return LocalDate.parse(date);
    }

    public static boolean parseYesOrNoToBoolean(final String input) {
        if (input.equals("Y")) {
            return true;
        }

        if (input.equals("N")) {
            return false;
        }

        throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
    }
}
