package store;

import static store.ErrorMessage.INVALID_INPUT;
import static store.ErrorMessage.INVALID_PURCHASE_ITEM_FORMAT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InputParser {
    private static final String PURCHASE_ITEMS_DELIMITER = ",";
    private static final String PURCHASE_ITEM_DELIMITER = "-";
    private static final String PURCHASE_ITEM_PREFIX = "[";
    private static final String PURCHASE_ITEM_SUFFIX = "]";
    private static final String TRUE_VALUE = "Y";
    private static final String FALSE_VALUE = "N";

    public static List<PurchaseItem> parsePurchaseItems(final String input) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (String purchaseItemInput : input.split(PURCHASE_ITEMS_DELIMITER)) {
            PurchaseItem purchaseItem = parsePurchaseItem(purchaseItemInput);
            if (purchaseItems.contains(purchaseItem)) {
                throw new IllegalArgumentException(INVALID_PURCHASE_ITEM_FORMAT.getMessage());
            }
            purchaseItems.add(purchaseItem);
        }
        return purchaseItems;
    }

    private static PurchaseItem parsePurchaseItem(final String purchaseItem) {
        validatePurchaseItemFormat(purchaseItem);
        String[] split = getPurchaseItemSubstring(purchaseItem).split(PURCHASE_ITEM_DELIMITER);
        if (split.length != 2) {
            throw new IllegalArgumentException(INVALID_PURCHASE_ITEM_FORMAT.getMessage());
        }
        String name = split[0];
        int quantity = parseInt(split[1]);
        return new PurchaseItem(name, quantity);
    }

    private static void validatePurchaseItemFormat(final String token) {
        if (!(token.startsWith(PURCHASE_ITEM_PREFIX) && token.endsWith(PURCHASE_ITEM_SUFFIX))) {
            throw new IllegalArgumentException(INVALID_PURCHASE_ITEM_FORMAT.getMessage());
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
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

    public static LocalDate parseLocalDate(final String date) {
        return LocalDate.parse(date);
    }

    public static boolean parseYesOrNoToBoolean(final String input) {
        if (input.equals(TRUE_VALUE)) {
            return true;
        }
        if (input.equals(FALSE_VALUE)) {
            return false;
        }
        throw new IllegalArgumentException(INVALID_INPUT.getMessage());
    }
}
