package store;

import static org.assertj.core.api.Assertions.*;
import static store.ErrorMessage.INVALID_INPUT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseItemTest {

    @DisplayName("PurchaseItem은_구매수량이_0개이면_예외를_발생한다")
    @Test
    public void should_ThrowException_WhenQuantityIsZero() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PurchaseItem("test", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_INPUT.getMessage());
    }

    @DisplayName("PurchaseItem은_상품명이_같으면_동일한_객체이다")
    @Test
    public void equals() {
        //given
        PurchaseItem purchaseItem = new PurchaseItem("test", 1);

        //when
        //then
        assertThat(purchaseItem).isEqualTo(new PurchaseItem("test", 3));
    }
}