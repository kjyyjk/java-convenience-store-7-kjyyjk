package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PurchaseHistoryDetailTest {

    @DisplayName("PurchaseHistoryDetail은_증정_수량의_유무를_반환할_수_있다")
    @CsvSource(value = {"1,True", "0,False"}, delimiter = ',')
    @ParameterizedTest
    public void hasBonusQuantity(int bonusQuantity, boolean expected) {
        //given
        PurchaseHistoryDetail detail = new PurchaseHistoryDetail("test", 1000, 3, 2, bonusQuantity);

        //when
        boolean result = detail.hasBonusQuantity();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PurchaseHistoryDetail은_구입_금액을_반환할_수_있다")
    @Test
    public void calculatePurchaseAmount() {
        //given
        PurchaseHistoryDetail detail = new PurchaseHistoryDetail("test", 1000, 4, 2, 4);

        //when
        int result = detail.calculatePurchaseAmount();

        //then
        assertThat(result).isEqualTo(4000);
    }

    @DisplayName("PurchaseHistoryDetail은_프로모션_할인_금액을_반환할_수_있다")
    @Test
    public void calculatePromotionDiscountAmount() {
        //given
        PurchaseHistoryDetail detail = new PurchaseHistoryDetail("test", 1000, 4, 2, 4);

        //when
        int result = detail.calculatePromotionDiscountAmount();

        //then
        assertThat(result).isEqualTo(4000);
    }

    @DisplayName("PurchaseHistoryDetail은_프로모션이_적용된_상품들의_총_금액을_반환할_수_있다")
    @Test
    public void calculatePromotionAppliedAmount() {
        //given
        PurchaseHistoryDetail detail = new PurchaseHistoryDetail("test", 1000, 4, 2, 4);

        //when
        int result = detail.calculatePromotionAppliedAmount();

        //then
        assertThat(result).isEqualTo(2000);
    }

    @DisplayName("PurchaseHistoryDetail은_구매_수량이_0인지_여부를_반환할_수_있다")
    @CsvSource(value = {"1,True", "0,False"}, delimiter = ',')
    @ParameterizedTest
    public void isNotZeroQuantity(int purchaseQuantity, boolean expected) {
        //given
        PurchaseHistoryDetail detail = new PurchaseHistoryDetail("test", 1000, purchaseQuantity, 2, 4);

        //when
        boolean result = detail.isNotZeroQuantity();

        //then
        assertThat(result).isEqualTo(expected);
    }
}