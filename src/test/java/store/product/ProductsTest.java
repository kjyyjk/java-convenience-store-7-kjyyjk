package store.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.ErrorMessage.EXCEED_QUANTITY_ERROR;
import static store.ErrorMessage.NOT_EXIST_PRODUCT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsTest {
    @DisplayName("Products는_존재하지_않는_상품의_이름을_받으면_예외를_발생한다")
    @Test
    public void should_ThrowException_WhenProductNotExists() {
        //given
        Map<String, GeneralProduct> generalProducts = new HashMap<>();
        Map<String, PromotionProduct> promotionProducts = new HashMap<>();
        generalProducts.put("potato", new GeneralProduct("potato", 2, 2));
        generalProducts.put("tomato", new GeneralProduct("tomato", 2, 2));
        promotionProducts.put("potato", new PromotionProduct("potato", 2, 2, null));

        Products products = new Products(generalProducts, promotionProducts);

        //when
        //then
        assertThatThrownBy(() -> products.validateProductExists("apple"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EXIST_PRODUCT.getMessage());
    }

    @DisplayName("Products는_구매_수량이_상품의_재고를_초과하면_예외를_발생한다")
    @Test
    public void should_ThrowException_WhenExceedProductQuantity() {
        //given
        Map<String, GeneralProduct> generalProducts = new HashMap<>();
        Map<String, PromotionProduct> promotionProducts = new HashMap<>();
        generalProducts.put("potato", new GeneralProduct("potato", 2, 2));
        generalProducts.put("tomato", new GeneralProduct("tomato", 2, 2));
        promotionProducts.put("potato", new PromotionProduct("potato", 2, 2, null));

        Products products = new Products(generalProducts, promotionProducts);

        //when
        //then
        assertThatThrownBy(() -> products.validateProductQuantity("potato", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EXCEED_QUANTITY_ERROR.getMessage());
    }
    
    @DisplayName("Products는_모든_상품을_반환할_수_있다")
    @Test
    public void getProducts() {
        //given
        Map<String, GeneralProduct> generalProducts = new HashMap<>();
        Map<String, PromotionProduct> promotionProducts = new HashMap<>();
        generalProducts.put("potato", new GeneralProduct("potato", 2, 2));
        generalProducts.put("tomato", new GeneralProduct("tomato", 2, 2));
        promotionProducts.put("potato", new PromotionProduct("potato", 2, 2, null));

        Products products = new Products(generalProducts, promotionProducts);

        //when
        List<Product> result = products.getProducts();

        //then
        assertThat(result.size()).isEqualTo(3);
    }
}