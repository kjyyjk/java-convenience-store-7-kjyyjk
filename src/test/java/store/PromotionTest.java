package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {

    @DisplayName("Promotion은_날짜가_프로모션_기간이면_True를_반환하고_아니면_False를_반환할_수_있다")
    @CsvSource(value = {"5,False", "7, True", "8,True", "12,True", "14, False"}, delimiter = ',')
    @ParameterizedTest
    public void isDoing(int input, boolean expected) {
        //given
        LocalDate startDate = LocalDate.of(2024, 11, 7);
        LocalDate endDate = LocalDate.of(2024, 11, 12);
        Promotion promotion = new Promotion("test", 2, 1, startDate, endDate);
        LocalDate date = LocalDate.of(2024, 11, input);

        //when
        boolean result = promotion.isDoing(date);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("Promotion은_프로모션_구매수량에_따른_추가_증정_수량을_반환할_수_있다")
    @CsvSource(value = {"2,1,3,0", "2,1,1,0", "2,1,2,1", "1,1,1,1", "1,1,2,0"}, delimiter = ',')
    @ParameterizedTest
    public void getExtraBonusQuantity(int buyQuantity, int bonusQuantity, int purchaseQuantity, int expected) {
        //given
        LocalDate now = LocalDate.now();
        Promotion promotion = new Promotion("test", buyQuantity, bonusQuantity, now, now);

        //when
        int result = promotion.getExtraBonusQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("Promotion은_프로모션_구매수량과_프로모션_재고에_따른_혜택_미적용_수량을_반환할_수_있다")
    @CsvSource(value = {"2,1,5,3,0", "2,1,1,3,3", "2,1,2,2,2", "1,1,2,1,0", "1,1,1,2,2"}, delimiter = ',')
    @ParameterizedTest
    public void getNotPromotionAppliedQuantity(int buyQuantity, int bonusQuantity, int promotionQuantity,
                                               int purchaseQuantity, int expected) {
        //given
        LocalDate now = LocalDate.now();
        Promotion promotion = new Promotion("test", buyQuantity, bonusQuantity, now, now);

        //when
        int result = promotion.getNotPromotionAppliedQuantity(promotionQuantity, purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("Promotion은_프로모션_구매_수량에_따른_증정_수량을_반환할_수_있다")
    @CsvSource(value = {"2,1,2,0", "2,1,9,3", "2,1,7,2", "1,1,4,2", "1,1,1,0", "1,1,5,2"}, delimiter = ',')
    @ParameterizedTest
    public void getTotalBonusQuantity(int buyQuantity, int bonusQuantity, int purchaseQuantity, int expected) {
        //given
        LocalDate now = LocalDate.now();
        Promotion promotion = new Promotion("test", buyQuantity, bonusQuantity, now, now);

        //when
        int result = promotion.getTotalBonusQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }
    
    @DisplayName("Promotion은_프로모션_구매_수량에_따른_혜택_적용_수량을_반환할_수_있다")
    @CsvSource(value = {"2,1,2,0", "2,1,9,9", "2,1,7,6", "1,1,4,4", "1,1,1,0", "1,1,5,4"}, delimiter = ',')
    @ParameterizedTest
    public void getAppliedQuantity(int buyQuantity, int bonusQuantity, int purchaseQuantity, int expected) {
        //given
        LocalDate now = LocalDate.now();
        Promotion promotion = new Promotion("test", buyQuantity, bonusQuantity, now, now);

        //when
        int result = promotion.getAppliedQuantity(purchaseQuantity);

        //then
        assertThat(result).isEqualTo(expected);
    }
}