package store.product;

import static store.ErrorMessage.EXCEED_QUANTITY_ERROR;
import static store.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.InputParser.parseYesOrNoToBoolean;
import static store.view.InputView.readPurchaseExtraPromotionQuantity;
import static store.view.InputView.readPurchasePromotionNotAppliedQuantity;
import static store.view.OutputView.printError;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import store.PurchaseHistoryDetail;

public class Products {
    private final Map<String, GeneralProduct> generalProducts;
    private final Map<String, PromotionProduct> promotionProducts;

    public Products(final Map<String, GeneralProduct> generalProducts,
                    final Map<String, PromotionProduct> promotionProducts) {
        this.generalProducts = generalProducts;
        this.promotionProducts = promotionProducts;
    }

    public void validateProductExists(final String productName) {
        GeneralProduct generalProduct = getGeneralProductByName(productName);
        PromotionProduct promotionProduct = getPromotionProductByName(productName);
        if (generalProduct == null && promotionProduct == null) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT.getMessage());
        }
    }

    public void validateProductQuantity(final String productName, final int purchaseQuantity) {
        if (isExceedTotalQuantity(productName, purchaseQuantity)) {
            throw new IllegalArgumentException(EXCEED_QUANTITY_ERROR.getMessage());
        }
    }

    public PurchaseHistoryDetail purchaseProduct(final String productName, final int purchaseQuantity) {
        int finalPurchaseQuantity = purchaseQuantity;
        int totalBonusQuantity = 0;
        int promotionAppliedQuantity = 0;
        PromotionProduct promotionProduct = getPromotionProductByName(productName);
        if (promotionProduct != null && promotionProduct.isDoingPromotion(getTodayDate())) {
            finalPurchaseQuantity = getFinalPurchaseQuantity(promotionProduct, purchaseQuantity);
            promotionAppliedQuantity = promotionProduct.getPromotionAppliedQuantity(finalPurchaseQuantity);
            totalBonusQuantity = promotionProduct.getTotalBonusQuantity(finalPurchaseQuantity);
        }

        decreaseProductQuantity(productName, finalPurchaseQuantity);
        return new PurchaseHistoryDetail(productName, getProductPriceByName(productName), finalPurchaseQuantity,
                promotionAppliedQuantity, totalBonusQuantity);
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        promotionProducts.values()
                .forEach(products::add);
        generalProducts.values()
                .forEach(products::add);
        products.sort(Comparator.comparing(Product::getName));
        return Collections.unmodifiableList(products);
    }

    private int getProductPriceByName(final String productName) {
        GeneralProduct generalProduct = getGeneralProductByName(productName);
        PromotionProduct promotionProduct = getPromotionProductByName(productName);
        if (generalProduct != null) {
            return generalProduct.getPrice();
        }
        return promotionProduct.getPrice();
    }

    private void decreaseProductQuantity(final String productName, final int quantity) {
        GeneralProduct generalProduct = getGeneralProductByName(productName);
        PromotionProduct promotionProduct = getPromotionProductByName(productName);
        int restQuantity = quantity;
        if (promotionProduct != null) {
            restQuantity -= promotionProduct.decreaseQuantity(quantity);
        }
        if (restQuantity != 0) {
            generalProduct.decreaseQuantity(restQuantity);
        }
    }

    private int getFinalPurchaseQuantity(final PromotionProduct promotionProduct, final int purchaseQuantity) {
        int extraBonusQuantity = promotionProduct.getExtraBonusQuantity(purchaseQuantity);
        if (isExceedTotalQuantity(promotionProduct.getName(), extraBonusQuantity + purchaseQuantity)) {
            return purchaseQuantity;
        }
        if (extraBonusQuantity > 0) {
            return purchaseQuantity + getExtraQuantity(promotionProduct.getName(), extraBonusQuantity);
        }
        int promotionNotAppliedQuantity = promotionProduct.getNotPromotionAppliedQuantity(purchaseQuantity);
        if (promotionNotAppliedQuantity > 0) {
            return purchaseQuantity - getExcludeQuantity(promotionProduct.getName(), promotionNotAppliedQuantity);
        }

        return purchaseQuantity;
    }

    private boolean isExceedTotalQuantity(final String productName, final int purchaseQuantity) {
        int totalQuantity = getTotalQuantityByName(productName);
        return totalQuantity < purchaseQuantity;
    }

    private int getTotalQuantityByName(final String productName) {
        GeneralProduct generalProduct = getGeneralProductByName(productName);
        PromotionProduct promotionProduct = getPromotionProductByName(productName);
        int totalQuantity = 0;
        if (generalProduct != null) {
            totalQuantity += generalProduct.getQuantity();
        }
        if (promotionProduct != null) {
            totalQuantity += promotionProduct.getQuantity();
        }
        return totalQuantity;
    }

    private GeneralProduct getGeneralProductByName(final String productName) {
        GeneralProduct generalProduct = generalProducts.getOrDefault(productName, null);
        return generalProduct;
    }

    private PromotionProduct getPromotionProductByName(final String productName) {
        PromotionProduct promotionProduct = promotionProducts.getOrDefault(productName, null);
        return promotionProduct;
    }

    private int getExtraQuantity(final String productName, final int extraBonusQuantity) {
        if (isWillGetExtraBonusQuantity(productName, extraBonusQuantity)) {
            return extraBonusQuantity;
        }
        return 0;
    }

    private int getExcludeQuantity(final String productName, final int promotionNotAppliedQuantity) {
        if (isWillPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity)) {
            return 0;
        }
        return promotionNotAppliedQuantity;
    }

    private boolean isWillPurchasePromotionNotAppliedQuantity(final String productName,
                                                              final int promotionNotAppliedQuantity) {
        try {
            return parseYesOrNoToBoolean(
                    readPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity));
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillPurchasePromotionNotAppliedQuantity(productName, promotionNotAppliedQuantity);
        }
    }

    private boolean isWillGetExtraBonusQuantity(final String productName, final int extraBonusQuantity) {
        try {
            return parseYesOrNoToBoolean(readPurchaseExtraPromotionQuantity(productName, extraBonusQuantity));
        } catch (IllegalArgumentException e) {
            printError(e);
            return isWillGetExtraBonusQuantity(productName, extraBonusQuantity);
        }
    }

    private LocalDate getTodayDate() {
        return DateTimes.now().toLocalDate();
    }
}
