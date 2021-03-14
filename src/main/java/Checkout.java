import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import basket.Basket;
import item.SimpleItem;
import pricing.PricingRule;

public class Checkout {
    private final List<PricingRule> pricingRuleList;
    private final Basket basket;

    public Checkout() {
        this.pricingRuleList = new ArrayList<>();
        this.basket = new Basket();
    }

    public List<PricingRule> getPricingRuleList() {
        return pricingRuleList;
    }

    /**
     * Adds one or more pricing rules, if a pricing rule for the same SKU existing the pricing rule will be updated
     * @param pricingRules - an array of pricing rules to add
     */
    public void addPricingRules(Set<PricingRule> pricingRules) {
        for (PricingRule pricingRule : pricingRules) {
            Optional<PricingRule> mayBePricingRule = searchPricingRuleBySku(pricingRule.getSku());

            // Check if a pricing rule exists for that SKU and update existing if so or add a new rule
            if(mayBePricingRule.isPresent()) {
                PricingRule rule = mayBePricingRule.get();
                rule.setUnitPrice(pricingRule.getUnitPrice());
                rule.setSpecialPrice(pricingRule.getSpecialPrice());
            } else {
                pricingRuleList.add(pricingRule);
            }
        }
    }

    /**
     * @param sku - The Stock Keeping Unit of the item's pricing rule to remove
     * @return boolean showing whether or not the pricing rule was successfully removed
     */
    public boolean removePricingRule(char sku) {
        return pricingRuleList.removeIf(pricingRule -> pricingRule.getSku() == sku);
    }

    /**
     * Scan an item through the checkout and adds the it to the basket
     * @param item - The item to scan
     * @return the new value of the basket
     * @throws IllegalArgumentException - if the pricing rule does not exist for the item
     */
    public int scan(SimpleItem item) throws IllegalArgumentException {
        Optional<PricingRule> mayBePricingRule = searchPricingRuleBySku(item.getSku());

        if (mayBePricingRule.isPresent()) {
            basket.addItem(item, mayBePricingRule.get());
        } else {
            throw new IllegalArgumentException();
        }
        return basket.getBasketValue();
    }

    private Optional<PricingRule> searchPricingRuleBySku(char sku) {
        return pricingRuleList.stream().filter(pricingRule -> pricingRule.getSku() == sku).findFirst();
    }
}
