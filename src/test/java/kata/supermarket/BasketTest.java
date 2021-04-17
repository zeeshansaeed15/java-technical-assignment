package kata.supermarket;

import kata.supermarket.discount.Discount;
import kata.supermarket.discount.DiscountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    private DiscountService discountService = Mockito.spy(new DiscountService());

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket(discountService);
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight()
        );
    }

    @DisplayName("basket provides its total value by applying discounts when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValueByAplyingBuyOneGetOneFreeDiscount(String description, String expectedTotal, Iterable<Item> items) {
        Discount discount = new Discount(Discount.BUY_ONE_GET_ONE_TXT);
        Mockito.doReturn(Optional.of(discount)).when(discountService).getProductAssociatedDiscount(2);
        Mockito.doReturn(Optional.of(discount)).when(discountService).getProductAssociatedDiscount(1);
        final Basket basket = new Basket(discountService);
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValueByAplyingBuyOneGetOneFreeDiscount() {
        return Stream.of(
                multipleItemsBuyOneGetOneFreePricedPerUnit(),
                multipleSameItemsBuyOneGetOneFreePricedPerUnit(),
                thriceSameItemsAndOneDifferentItemForBuyOneGetOneFreePricedPerUnit(),
                fourTimesSameItemsAndOneDifferentItemForBuyOneGetOneFreePricedPerUnit()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments multipleItemsBuyOneGetOneFreePricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk(), aPackOfDigestives()));
    }

    private static Arguments multipleSameItemsBuyOneGetOneFreePricedPerUnit() {
        return Arguments.of("multiple same items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk(), aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments thriceSameItemsAndOneDifferentItemForBuyOneGetOneFreePricedPerUnit() {
        return Arguments.of("thrice same product and one different product", "3.59",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk(), aPackOfDigestives(), aPackOfDigestives()));
    }

    private static Arguments fourTimesSameItemsAndOneDifferentItemForBuyOneGetOneFreePricedPerUnit() {
        return Arguments.of("four times same product and one different product", "3.59",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk(), aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new UnitProduct(new BigDecimal("0.49"), 1).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new UnitProduct(new BigDecimal("1.55"), 2).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"), 3);
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"), 4);
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}