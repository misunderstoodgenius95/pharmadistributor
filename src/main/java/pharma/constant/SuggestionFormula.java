package pharma.constant;

public enum SuggestionFormula {
    LOW(0),
    HIGHT(1),
    MIDDLE(0.5),
    MIN_STOCK_DAY(3),
    WT(0.10),
    MIN_DAY_EXPIRE_DAY(180),
    GAIN(20);



     final   double value;

    SuggestionFormula(double value) {
        this.value=value;
    }

    public double getValue() {
        return value;
    }


}
