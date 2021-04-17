package kata.supermarket.discount;

import kata.supermarket.Product;
import kata.supermarket.UnitProduct;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class DiscountProductTest {

    @Test
    public void testCreateDiscountProduct() {
        DiscountProduct discountProduct = new DiscountProduct(setupProduct(), setupDiscount());

        Assertions.assertEquals(Discount.BUY_ONE_GET_ONE_TXT, discountProduct.getDiscount().getDiscountDescription());
        Assertions.assertEquals(1, discountProduct.getProduct().getId());
    }

    private Product setupProduct() {
        return new UnitProduct(new BigDecimal("12.0"), 1);
    }

    private Discount setupDiscount() {
        return new Discount(Discount.BUY_ONE_GET_ONE_TXT);
    }
}
