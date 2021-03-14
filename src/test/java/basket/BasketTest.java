package basket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import item.SimpleItem;
import pricing.PricingRule;
import pricing.SpecialPrice;

class BasketTest {
    private Basket basket;

    @BeforeEach
    private void setup() {
        basket = new Basket();
    }

    @Test
    public void shouldCorrectlyAddItem() {
        PricingRule pricingRule = new PricingRule('a', 3);
        basket.addItem(new SimpleItem('a'), pricingRule);

        assertEquals(pricingRule.getUnitPrice(), basket.getBasketValue());
    }

    @Test
    public void shouldCorrectlyDiscountItem() {
        SpecialPrice specialPrice = new SpecialPrice(3, 10);
        PricingRule pricingRule = new PricingRule('b', 5);
        pricingRule.setSpecialPrice(specialPrice);

        for (int i = 0; i < pricingRule.getSpecialPrice().getAmountNeeded(); i++) {
            basket.addItem(new SimpleItem('b'), pricingRule);
        }

        assertEquals(pricingRule.getSpecialPrice().getPrice(), basket.getBasketValue());
    }
}