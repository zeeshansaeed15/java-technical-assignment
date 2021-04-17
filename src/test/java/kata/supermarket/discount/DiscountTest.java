package kata.supermarket.discount;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DiscountTest {

    @Test
    public void createDiscount() {
        Discount discount = new Discount(Discount.BUY_ONE_GET_ONE_TXT);

        Assertions.assertEquals(Discount.BUY_ONE_GET_ONE_TXT, discount.getDiscountDescription());
    }
}
