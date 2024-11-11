package store.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.Promotion;

class PromotionProductTest {

    @DisplayName("PromotionProduct는_재고_수량과_구매_수량_프로모션_정책에_따른_추가_증정_수량을_반환할_수_있다")
    @CsvSource(value = {"6,0", "5,1", "12,0", "10,0"}, delimiter = ',')
    @ParameterizedTest
    public void getExtraBonusQuantity(int purchaseQuantity, int expected) {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        int result = promotionProduct.getExtraBonusQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_재고_수량과_구매_수량_프로모션_정책에_따른_프로모션_미적용_수량을_반환할_수_있다")
    @CsvSource(value = {"6,0", "5,2", "12,1", "10,1"}, delimiter = ',')
    @ParameterizedTest
    public void getNotPromotionAppliedQuantity(int purchaseQuantity, int expected) {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        int result = promotionProduct.getNotPromotionAppliedQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_날짜를_기준으로_프로모션이_진행중인지_확인할_수_있다")
    @CsvSource(value = {"6,False", "7,True", "10,True", "12,True", "13, False"}, delimiter = ',')
    @ParameterizedTest
    public void isDoingPromotion(int input, boolean expected) {
        //given
        LocalDate startDate = LocalDate.of(2024, 11, 7);
        LocalDate endDate = LocalDate.of(2024, 11, 12);
        Promotion promotion = new Promotion("test", 2, 1, startDate, endDate);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);
        LocalDate date = LocalDate.of(2024, 11, input);

        //when
        boolean result = promotionProduct.isDoingPromotion(date);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_재고_수량과_구매_수량_프로모션_정책에_따른_프로모션_적용_수량을_반환할_수_있다")
    @CsvSource(value = {"6,6", "5,3", "12,9", "10,9"}, delimiter = ',')
    @ParameterizedTest
    public void getPromotionAppliedQuantity(int purchaseQuantity, int expected) {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        int result = promotionProduct.getPromotionAppliedQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_재고_수량과_구매_수량_프로모션_정책에_따른_프로모션_증정_수량을_반환할_수_있다")
    @CsvSource(value = {"6,2", "5,1", "12,3", "10,3"}, delimiter = ',')
    @ParameterizedTest
    public void getTotalBonusQuantity(int purchaseQuantity, int expected) {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        int result = promotionProduct.getTotalBonusQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_재고_수량과_구매_수량에_따른_재고_차감_수량을_반환할_수_있다")
    @CsvSource(value = {"6,6", "12,10", "10,10"}, delimiter = ',')
    @ParameterizedTest
    public void decreaseQuantity(int quantity, int expected) {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        int result = promotionProduct.decreaseQuantity(quantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("PromotionProduct는_프로모션의_이름을_반환할_수_있다")
    @Test
    public void getPromotionName() {
        //given
        Promotion promotion = new Promotion("test", 2, 1, LocalDate.MIN, LocalDate.MAX);
        PromotionProduct promotionProduct = new PromotionProduct("potato", 1000, 10, promotion);

        //when
        String result = promotionProduct.getPromotionName();

        //then
        assertThat(result).isEqualTo("test");
    }
}