package pricing;

public class SpecialPrice {
    private final int amountNeeded;
    private final int price;

    public SpecialPrice(int amountNeeded, int price) {
        if (amountNeeded <= 1) {
            throw new IllegalArgumentException("Amount needed must be more than one");
        }
        this.amountNeeded = amountNeeded;
        if (price < 1) {
            throw new IllegalArgumentException("Special price cannot be less than one");
        }
        this.price = price;
    }

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public int getPrice() {
        return price;
    }
}
