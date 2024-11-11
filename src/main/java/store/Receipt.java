package store;

public enum Receipt {
    HEADER("\n==============W 편의점================"),
    PURCHASE_HISTORY_DETAIL_FOOTER("상품명           수량           금액"),
    PURCHASE_HISTORY_DETAIL_FORMAT("%-13s%3d            %,-6d"),
    PROMOTION_HEADER("=============증\t\t정==============="),
    PROMOTION_FORMAT("%-13s%3d"),
    PROMOTION_FOOTER("===================================="),
    TOTAL_PURCHASE_AMOUNT_FORMAT("총구매액%12d            %,-6d"),
    PROMOTION_DISCOUNT_AMOUNT_FORMAT("행사할인                        -%,-6d"),
    MEMBERSHIP_DISCOUNT_AMOUNT_FORMAT("멤버십할인                       -%,-6d"),
    PAY_AMOUNT_FORMAT("내실돈                          %,-6d");

    private final String message;

    Receipt(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
