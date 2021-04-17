package kata.supermarket.discount;

import kata.supermarket.Item;
import kata.supermarket.Product;
import kata.supermarket.UnitProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountServiceTest {

    private DiscountService discountService;

    @Before
    public void setup() {
        discountService = Mockito.spy(new DiscountService());
    }

    @Test
    public void whenNoDiscountProductsAvailableThenReturnEmpty() {
        int productId = 1;

        Optional<Discount> optionalDiscount = discountService.getProductAssociatedDiscount(productId);

        Assertions.assertFalse(optionalDiscount.isPresent());
    }

    @Test
    public void whenDiscountAvailableThenReturnProductDiscount() {
        Mockito.doReturn(setupDiscountProduct()).when(discountService).getDiscountProducts();
        int productId = 1;

        Discount discount = discountService.getProductAssociatedDiscount(productId).get();

        Assertions.assertEquals(Discount.BUY_ONE_GET_ONE_TXT, discount.getDiscountDescription());
    }

    private List<DiscountProduct> setupDiscountProduct() {

        DiscountProduct discountProduct = new DiscountProduct(setupProduct(), setupDiscount());

        List<DiscountProduct> discountProducts = new ArrayList<>();
        discountProducts.add(discountProduct);

        return discountProducts;
    }

    private Product setupProduct() {
        return new UnitProduct(new BigDecimal("12.0"), 1);
    }

    private Discount setupDiscount() {
        return new Discount(Discount.BUY_ONE_GET_ONE_TXT);
    }

    private List<Item> setupOneItem() {
        UnitProduct unitProduct = (UnitProduct) setupProduct();
        Item unitItem = unitProduct.oneOf();

        List<Item> items = new ArrayList<>();
        items.add(unitItem);

        return items;
    }

    private List<Item> setupTwoSameItems() {
        UnitProduct unitProduct1 = (UnitProduct) setupProduct();
        Item unitItem1 = unitProduct1.oneOf();

        UnitProduct unitProduct2 = (UnitProduct) setupProduct();
        Item unitItem2 = unitProduct2.oneOf();

        List<Item> items = new ArrayList<>();
        items.add(unitItem1);
        items.add(unitItem2);

        return items;
    }

    private List<Item> setupThreeSameItems() {
        UnitProduct unitProduct1 = (UnitProduct) setupProduct();
        Item unitItem1 = unitProduct1.oneOf();

        UnitProduct unitProduct2 = (UnitProduct) setupProduct();
        Item unitItem2 = unitProduct2.oneOf();

        UnitProduct unitProduct3 = (UnitProduct) setupProduct();
        Item unitItem3 = unitProduct3.oneOf();

        List<Item> items = new ArrayList<>();
        items.add(unitItem1);
        items.add(unitItem2);
        items.add(unitItem3);

        return items;
    }

    @Test
    public void whenSingleProductForBuyOneGetOneFreeThenReturnZeroDiscount() {
        Assertions.assertEquals(BigDecimal.ZERO, discountService.calculateBuyOneGetOneFreeDiscount(setupOneItem(), setupProduct()));
    }

    @Test
    public void whenTwoSameProductstForBuyOneGetOneFreeThenReturnHalfPrice() {
        Assertions.assertEquals(new BigDecimal("12.0"), discountService.calculateBuyOneGetOneFreeDiscount(setupTwoSameItems(), setupProduct()));
    }

    @Test
    public void whenThreeSameProductstForBuyOneGetOneFreeThenReturnHalfPriceDiscountForTwoProducts() {
        Assertions.assertEquals(new BigDecimal("12.0"), discountService.calculateBuyOneGetOneFreeDiscount(setupThreeSameItems(), setupProduct()));
    }
}
