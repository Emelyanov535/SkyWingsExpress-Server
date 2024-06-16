package ru.swe.skywingsexpressserver.model.user;

public enum BenefitEnum {
    PENSIONER(30),
    CHILD(50),
    STUDENT(25);

    private final Integer discount;
    BenefitEnum(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscountValue() {
        return discount;
    }
}
