package kata.supermarket.discount;

import kata.supermarket.Product;

public class DiscountProduct {
    private Product product;
    private Discount discount;
    //private int quantity;

    DiscountProduct(Product product, Discount discount) {
        this.product = product;
        this.discount = discount;
       // this.quantity = quantity;
    }

    Discount getDiscount() {
        return this.discount;
    }

    Product getProduct() {
        return product;
    }
}