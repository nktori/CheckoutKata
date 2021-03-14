package item;

public class SimpleItem implements Item {
    private final char sku;

    public SimpleItem(char sku) {
        this.sku = sku;
    }

    public char getSku() {
        return sku;
    }
}
