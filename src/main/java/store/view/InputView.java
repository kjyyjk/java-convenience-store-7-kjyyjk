package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String READ_PURCHASE_ITEM_MESSAGE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String READ_EXTRA_PROMOTION_QUANTITY_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String READ_NO_PROMOTION_QUANTITY_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";

    public static String readPurchaseItems() {
        System.out.println(READ_PURCHASE_ITEM_MESSAGE);
        return input();
    }

    public static String readExtraPromotionQuantity(final String name, final int extraBonusQuantity) {
        String message = READ_EXTRA_PROMOTION_QUANTITY_MESSAGE.formatted(name, extraBonusQuantity);
        System.out.println(message);
        return input();
    }

    public static String input() {
        return Console.readLine();
    }

    public static String readNoPromotionQuantity(final String name, final int noPromotionQuantity) {
        String message = READ_NO_PROMOTION_QUANTITY_MESSAGE.formatted(name, noPromotionQuantity);
        System.out.println(message);
        return input();
    }
}