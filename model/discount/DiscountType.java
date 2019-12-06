package model.discount;

public enum DiscountType {
    GROUP("model.discount.GroupDiscount"),
    THRESHOLD("model.discount.ThresholdDiscount"),
    MOST_EXPENSIVE("model.discount.MostExpensiveDiscount");

    private final String className;

    DiscountType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}