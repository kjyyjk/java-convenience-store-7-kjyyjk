package store;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionsTest {

    @DisplayName("Promotions는_Promotion의_이름으로_Promotion_객체를_반환할_수_있다")
    @Test
    public void get() {
        //given
        Promotion promotion = new Promotion("test", 1, 2, LocalDate.now(), LocalDate.now());
        Promotions promotions = new Promotions(Map.of(promotion.getName(), promotion));

        //when
        Promotion result = promotions.get(promotion.getName());

        //then
        assertThat(result).isEqualTo(promotion);
    }

    @DisplayName("Promotions는_존재하지_않는_Promotion_이름에_대해서는_null을_반환한다")
    @Test
    public void should_ReturnNull_WhenKeyIsNotExist() {
        //given
        Promotion promotion = new Promotion("test", 1, 2, LocalDate.now(), LocalDate.now());
        Promotions promotions = new Promotions(Map.of(promotion.getName(), promotion));

        //when
        Promotion result = promotions.get("hi");

        //then
        assertThat(result).isNull();
    }
}