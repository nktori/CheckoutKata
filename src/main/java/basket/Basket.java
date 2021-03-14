package basket;

import java.util.HashMap;
import java.util.Map;

import item.Item;
import pricing.PricingRule;

public class Basket {

    private int basketValue = 0;
    private final Map<Character, Integer> itemCountMap = new HashMap<>();

    public void addItem(Item item, PricingRule pricingRule) {
        char itemSku = item.getSku();
        int count = itemCountMap.getOrDefault(itemSku, 0);
        itemCountMap.put(itemSku, count + 1);

        // Check if the item has been added a multiple of the times needed for the special price
        if (itemCountMap.containsKey(itemSku)
                && pricingRule.getSpecialPrice() != null
                && itemCountMap.get(itemSku) % pricingRule.getSpecialPrice().getAmountNeeded() == 0) {
            // subtract the value of the items that will be included in the special price
            // that have been added previously before adding the special price
            basketValue -= pricingRule.getUnitPrice() * (pricingRule.getSpecialPrice().getAmountNeeded() - 1);
            basketValue += pricingRule.getSpecialPrice().getPrice();
        } else {
            basketValue += pricingRule.getUnitPrice();
        }
    }

    public int getBasketValue() {
        return basketValue;
    }
}
