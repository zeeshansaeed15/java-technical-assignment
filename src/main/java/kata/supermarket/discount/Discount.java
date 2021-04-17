package kata.supermarket.discount;

public class Discount {
    public static final String BUY_ONE_GET_ONE_TXT = "Buy one, get one free";
    public static final String BUY_TWO_ITEMS_FOR_ONE_POUND = "Buy two items for £1";
    public static final String BUY_THREE_ITEMS_FOR_THE_PRICE_OF_TWO = "Buy three items for the price of two";
    public static final String BUY_ONE_KILO_VEGETABLES_FOR_HALF_PRICE = "Buy one kilo of vegetables for half price";

    private String discountDescription;

    public Discount(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }
}
