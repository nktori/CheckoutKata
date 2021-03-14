import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import item.SimpleItem;
import pricing.PricingRule;

class CheckoutTest {
    private static final pricing.PricingRule PRICING_RULE = new PricingRule('a', 3);

    private Checkout checkout;

    @BeforeEach
    private void setup() {
        checkout = new Checkout();
    }

    @Test
    public void newPricingRuleCanBeAdded() {
        checkout.addPricingRules(singleton(PRICING_RULE));

        assertEquals(1, checkout.getPricingRuleList().size());
    }

    @Test
    public void multiplePricingRulesCanBeAdded() {
        PricingRule pricingRule = new PricingRule('b', 5);
        Set<PricingRule> pricingRuleSet = new HashSet<>();
        pricingRuleSet.add(PRICING_RULE);
        pricingRuleSet.add(pricingRule);
        checkout.addPricingRules(pricingRuleSet);

        assertEquals(2, checkout.getPricingRuleList().size());
    }

    @Test
    public void pricingRuleCanBeModified() {
        checkout.addPricingRules(singleton(PRICING_RULE));
        PricingRule pricingRule = new PricingRule('a', 5);
        checkout.addPricingRules(singleton(pricingRule));

        assertEquals(1, checkout.getPricingRuleList().size());
        assertEquals(5.0, checkout.getPricingRuleList().get(0).getUnitPrice());
    }

    @Test
    public void pricingRuleCanBeRemoved() {
        checkout.addPricingRules(singleton(PRICING_RULE));
        boolean wasRemoved = checkout.removePricingRule('a');

        assertTrue(wasRemoved);
        assertTrue(checkout.getPricingRuleList().isEmpty());
    }

    @Test
    public void nonExistingItemFailsToRemoveGracefully() {
        boolean wasRemoved = checkout.removePricingRule('a');

        assertFalse(wasRemoved);
    }

    @Test
    public void canScanExistingItem() throws IllegalArgumentException {
        checkout.addPricingRules(singleton(PRICING_RULE));
        // we are not testing the price logic of the basket here, just that it adds successfully
        // could mock the basket as to not couple the logic in the test to two classes and then assert the output
        assertDoesNotThrow(() -> checkout.scan(new SimpleItem('a')));
    }

    @Test
    public void scanningNonExistingItemsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> checkout.scan(new SimpleItem('a')));
    }
}
