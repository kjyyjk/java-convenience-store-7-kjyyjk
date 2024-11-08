package store;

import java.util.Map;

public class Promotions {
    private Map<String, Promotion> promotions;

    public Promotions(final Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion get(final String name) {
        return promotions.getOrDefault(name, null);
    }
}
