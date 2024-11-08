package store;

import java.util.Map;

public class Promotions {
    private Map<String, Promotion> promotions;

    public Promotions(Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion get(String name) {
        return promotions.getOrDefault(name, null);
    }
}
