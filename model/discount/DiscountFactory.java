package model.discount;

/**
 * @author Justė Naujokaitytė
 */
public class DiscountFactory {
    private static DiscountFactory instance;

    private DiscountFactory() {
    }

    public static DiscountFactory getInstance() {
        if (instance == null) {
            instance = new DiscountFactory();
        }
        return instance;
    }

    public DiscountStrategy createDiscountStrategy(String discountType, double percentage, String discountAdditional) {
        DiscountType discountTypeEnum = DiscountType.valueOf(discountType.toUpperCase());
        String discountClassName = discountTypeEnum.getClassName();
        DiscountStrategy discountStrategy = null;
        try {
            Class discountClass = Class.forName(discountClassName);
            discountStrategy = (DiscountStrategy) discountClass.newInstance();
            discountStrategy.setPercentage(percentage);
            if (discountStrategy instanceof ThresholdDiscount) {
                ((ThresholdDiscount) discountStrategy).setAmount(Double.parseDouble(discountAdditional));
            } else if (discountStrategy instanceof GroupDiscount) {
                ((GroupDiscount) discountStrategy).setGroup(discountAdditional);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return discountStrategy;
    }

}
