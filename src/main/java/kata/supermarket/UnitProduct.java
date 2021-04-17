package kata.supermarket;

import java.math.BigDecimal;

public class UnitProduct extends Product {
    private final BigDecimal pricePerUnit;

    public UnitProduct(final BigDecimal pricePerUnit, final Integer productId) {
        super(productId);
        this.pricePerUnit = pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }

    @Override
    public BigDecimal price() {
        return pricePerUnit;
    }
}
