package pricing;

import java.util.Optional;

public class PricingRule {
    private final char sku;
    private int unitPrice;
    private Optional<SpecialPrice> specialPrice = Optional.empty();

    public PricingRule(char sku, int unitPrice) {
        this.sku = sku;
        this.unitPrice = unitPrice;
    }

    public char getSku() {
        return sku;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public SpecialPrice getSpecialPrice() {
        return specialPrice.orElse(null);
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSpecialPrice(SpecialPrice specialPrice) {
        this.specialPrice = Optional.ofNullable(specialPrice);
    }
}
