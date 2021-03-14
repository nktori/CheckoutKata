import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import item.SimpleItem;
import pricing.PricingRule;
import pricing.SpecialPrice;

public class CheckoutIT {
    private static final SpecialPrice SPECIAL_PRICE = new SpecialPrice(3, 5);

    private Checkout checkout;

    @BeforeEach
    private void setup() {
        checkout = new Checkout();
    }

    @Test
    public void singleItemScans() {
        SimpleItem item = addSingleItemAndPricingRule('a', 3, null);

        assertEquals(3, checkout.scan(item));
        assertEquals(6, checkout.scan(item));
        assertEquals(9, checkout.scan(item));
    }

    @Test
    public void singleItemScansWithSpecialPrice() {
        SimpleItem item = addSingleItemAndPricingRule('a', 3, SPECIAL_PRICE);

        assertEquals(3, checkout.scan(item));
        assertEquals(6, checkout.scan(item));
        assertEquals(SPECIAL_PRICE.getPrice(), checkout.scan(item));
    }

    @Test
    public void multipleItemsScan() {
        SimpleItem item = addSingleItemAndPricingRule('a', 3, null);
        SimpleItem item2 = addSingleItemAndPricingRule('b', 2, null);

        assertEquals(3, checkout.scan(item));
        assertEquals(5, checkout.scan(item2));
        assertEquals(7, checkout.scan(item2));
        assertEquals(10, checkout.scan(item));
    }

    @Test
    public void multipleItemsScanWithSpecialPrice() {
        SimpleItem item = addSingleItemAndPricingRule('a', 3, SPECIAL_PRICE);
        SimpleItem item2 = addSingleItemAndPricingRule('b', 2, new SpecialPrice(2, 3));

        assertEquals(3, checkout.scan(item));
        assertEquals(5, checkout.scan(item2));
        assertEquals(6, checkout.scan(item2));
        assertEquals(9, checkout.scan(item));
        assertEquals(8, checkout.scan(item));
    }

    private SimpleItem addSingleItemAndPricingRule(char sku, int price, SpecialPrice specialPrice) {
        PricingRule pricingRule = new PricingRule(sku, price);
        pricingRule.setSpecialPrice(specialPrice);
        checkout.addPricingRules(singleton(pricingRule));

        return new SimpleItem(sku);
    }
}
