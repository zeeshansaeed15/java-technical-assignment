package kata.supermarket;

import kata.supermarket.discount.Discount;
import kata.supermarket.discount.DiscountService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Basket {
    private final List<Item> items;
    private DiscountService discountService;

    public Basket(DiscountService discountService) {
        this.items = new ArrayList<>();
        this.discountService = discountService;
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * TODO: This could be a good place to apply the results of
         *  the discount calculations.
         *  It is not likely to be the best place to do those calculations.
         *  Think about how Basket could interact with something
         *  which provides that functionality.
         */
        private BigDecimal discounts() {
            Set<Integer> alreadyIteratedProduct = new HashSet<>();
            BigDecimal totalDiscount = new BigDecimal(String.valueOf(BigDecimal.ZERO));
            for (Item item : items) {
                Product product = item.getProduct();
                Optional<Discount> associatedDiscount = discountService.getProductAssociatedDiscount(product.getId());
                if (associatedDiscount.isPresent()) {
                    if (associatedDiscount.get().getDiscountDescription().equals(Discount.BUY_ONE_GET_ONE_TXT)) {
                        if (!alreadyIteratedProduct.contains(product.getId())) {
                            alreadyIteratedProduct.add(product.getId());
                            totalDiscount = totalDiscount.add(discountService.calculateBuyOneGetOneFreeDiscount(items, product));
                        }
                    }
                }
            }
            return totalDiscount;
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
