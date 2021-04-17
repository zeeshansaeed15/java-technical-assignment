package kata.supermarket;

import java.math.BigDecimal;

public class WeighedProduct extends Product {

    private final BigDecimal pricePerKilo;

    public WeighedProduct(final BigDecimal pricePerKilo, final Integer productId) {
        super(productId);
        this.pricePerKilo = pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }

    @Override
    public BigDecimal price() {
        return pricePerKilo;
    }
}
