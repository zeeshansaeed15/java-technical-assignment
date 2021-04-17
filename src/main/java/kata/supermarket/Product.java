package kata.supermarket;

import java.math.BigDecimal;

public abstract class Product {
    private Integer id;

    Product(Integer id) {
        this.id = id;
    }

    public abstract BigDecimal price();

    public Integer getId() {
        return id;
    }
}
