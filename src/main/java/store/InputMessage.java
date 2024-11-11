package store;

public enum InputMessage {
    READ_PURCHASE_ITEM_MESSAGE("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    READ_EXTRA_PROMOTION_QUANTITY_MESSAGE("\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    READ_NO_PROMOTION_QUANTITY_MESSAGE("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    READ_MEMBERSHIP_DISCOUNT_MESSAGE("\n멤버십 할인을 받으시겠습니까? (Y/N)"),
    READ_ADDITIONAL_PURCHASE_MESSAGE("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String message;

    InputMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
