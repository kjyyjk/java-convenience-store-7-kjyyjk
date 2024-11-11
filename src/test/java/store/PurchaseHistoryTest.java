package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseHistoryTest {

    @DisplayName("PurchaseHistory는_수량이_존재하는_PurchaseHistoryDetail만을_반환할_수_있다")
    @Test
    public void getPurchaseHistoryDetails() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 0, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        List<PurchaseHistoryDetail> result = purchaseHistory.getPurchaseHistoryDetails();

        //then
        assertThat(result).containsExactlyInAnyOrder(detail2, detail3);
    }

    @DisplayName("PurchaseHistory는_구매_수량이_존재하고_증정_수량이_존재하는_PurchaseHistoryDetail만을_반환할_수_있다")
    @Test
    public void getBonusPurchaseHistoryDetails() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 0, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        List<PurchaseHistoryDetail> result = purchaseHistory.getBonusPurchaseHistoryDetails();

        //then
        assertThat(result).containsExactly(detail3);
    }

    @DisplayName("PurchaseHistory는_총_구매_수량을_반환할_수_있다")
    @Test
    public void calculateTotalPurchaseQuantity() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 10, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculateTotalPurchaseQuantity();

        //then
        assertThat(result).isEqualTo(25);
    }

    @DisplayName("PurchaseHistory는_총_구매_금액을_반환할_수_있다")
    @Test
    public void calculateTotalPurchaseAmount() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 10, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculateTotalPurchaseAmount();

        //then
        assertThat(result).isEqualTo(35000);
    }

    @DisplayName("PurchaseHistory는_프로모션_할인_금액을_반환할_수_있다")
    @Test
    public void calculatePromotionDiscountAmount() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 10, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculatePromotionDiscountAmount();

        //then
        assertThat(result).isEqualTo(1000);
    }

    @DisplayName("PurchaseHistory는_멤버십_할인_금액을_계산할_수_있다")
    @Test
    public void calculateMembershipDiscountAmount() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 5, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 5, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculateMembershipDiscountAmount();

        //then
        assertThat(result).isEqualTo((int) (18000 * 0.3));
    }
    
    @DisplayName("PurchaseHistory는_멤버십_할인_금액을_8000원으로_제한할_수_있다")
    @Test
    public void should_ReturnLimit_WhenExceedLimit() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 10, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculateMembershipDiscountAmount();

        //then
        assertThat(result).isEqualTo(8000);
    }

    @DisplayName("PurchaseHistory는_프로모션과_멤버십_할인이 적용된_지불_금액을_계산할_수_있다")
    @Test
    public void calculatePayAmount() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 10, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 10, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 5, 2, 1);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        int result = purchaseHistory.calculatePayAmount();

        //then
        assertThat(result).isEqualTo(26000);
    }

    @DisplayName("PurchaseHistory는_모든_구매_수량이_0인지_확인할_수_있다")
    @Test
    public void isTotalPurchaseQuantityEqualsZero() {
        //given
        PurchaseHistoryDetail detail1 = new PurchaseHistoryDetail("potato", 1000, 0, 0, 0);
        PurchaseHistoryDetail detail2 = new PurchaseHistoryDetail("tomato", 2000, 0, 0, 0);
        PurchaseHistoryDetail detail3 = new PurchaseHistoryDetail("apple", 1000, 0, 0, 0);

        PurchaseHistory purchaseHistory = new PurchaseHistory(List.of(detail1, detail2, detail3), true);

        //when
        boolean result = purchaseHistory.isTotalPurchaseQuantityEqualsZero();

        //then
        assertThat(result).isTrue();
    }
}