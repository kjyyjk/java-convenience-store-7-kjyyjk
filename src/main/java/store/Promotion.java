package store;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buyQuantity;
    private final int bonusQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String name, final int buyQuantity, final int bonusQuantity, final LocalDate startDate,
                     final LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.bonusQuantity = bonusQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public boolean isDoing(final LocalDate date) {
        if (date.isAfter(endDate) || date.isBefore(startDate)) {
            return false;
        }
        return true;
    }

    public int getExtraQuantity(final int purchaseQuantity) {
        if ((purchaseQuantity % (buyQuantity + bonusQuantity)) >= buyQuantity) {
            return bonusQuantity;
        }
        return 0;
    }

    public int getNoPromotionQuantity(final int promotionQuantity, final int purchaseQuantity) {
        return purchaseQuantity - ((buyQuantity + bonusQuantity) * (promotionQuantity / (buyQuantity + bonusQuantity)));
    }
}