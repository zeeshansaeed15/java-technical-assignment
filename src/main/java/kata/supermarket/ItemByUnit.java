package kata.supermarket;

import java.math.BigDecimal;

public class ItemByUnit implements Item {

    private final UnitProduct unitProduct;

    ItemByUnit(final UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public BigDecimal price() {
        return unitProduct.price();
    }

    @Override
    public UnitProduct getProduct() {
        return this.unitProduct;
    }
}
