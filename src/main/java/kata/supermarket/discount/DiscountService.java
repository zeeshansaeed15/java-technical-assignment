package kata.supermarket.discount;

import kata.supermarket.Item;
import kata.supermarket.Product;
import kata.supermarket.UnitProduct;

import java.math.BigDecimal;
import java.util.*;

public class DiscountService {
    private final static int HALF = 2;

    public Optional<Discount> getProductAssociatedDiscount(int productId) {
        for (DiscountProduct discountProduct : this.getDiscountProducts()) {
            if (discountProduct.getProduct().getId().intValue() == productId) {
                return Optional.of(discountProduct.getDiscount());
            }
        }
        return Optional.empty();
    }

    protected List<DiscountProduct> getDiscountProducts() {
        return DiscountProductRepository.getDiscountProducts();
    }

    public BigDecimal calculateDiscount(List<Item> items) {
        Set<Integer> alreadyIteratedProduct = new HashSet<>();
        BigDecimal totalDiscount = new BigDecimal(String.valueOf(BigDecimal.ZERO));
        for (Item item : items) {
            Product product = item.getProduct();
            Optional<Discount> associatedDiscount = this.getProductAssociatedDiscount(product.getId());
            if (associatedDiscount.isPresent()) {
                if (associatedDiscount.get().getDiscountDescription().equals(Discount.BUY_ONE_GET_ONE_TXT)) {
                    if (!alreadyIteratedProduct.contains(product.getId())) {
                        alreadyIteratedProduct.add(product.getId());
                        totalDiscount = totalDiscount.add(this.calculateBuyOneGetOneFreeDiscount(items, product));
                    }
                }
            }
        }
        return totalDiscount;
    }

    public BigDecimal calculateBuyOneGetOneFreeDiscount(List<Item> items, Product product) {
        Map<Integer, Integer> productQuantityMap = initialiseProductQuantityMap(items);
        int productQuantity = productQuantityMap.get(product.getId());
        if (productQuantity > 1) {
            if (productQuantity % 2 == 0) {
                BigDecimal totalProductPrice = product.price().multiply(new BigDecimal(productQuantity));
                return totalProductPrice.divide(new BigDecimal(HALF));
            } else {
                BigDecimal totalProductPrice = product.price().multiply(new BigDecimal(productQuantity - 1));
                return totalProductPrice.divide(new BigDecimal(HALF));
            }
        }
        return BigDecimal.ZERO;
    }

    private static Map<Integer, Integer> initialiseProductQuantityMap(List<Item> items) {
        Map<Integer, Integer> productQuantityMap = new HashMap<>();
        items.stream().forEach(item -> {
            UnitProduct unitProduct = (UnitProduct) item.getProduct();
            Integer key = unitProduct.getId();
            int quantity = 1;
            if (productQuantityMap.containsKey(key)) {
                quantity = productQuantityMap.get(key) + 1;
            }
            productQuantityMap.put(key, quantity);
        });
        return productQuantityMap;
    }
}
