package pharma.config;

public enum PathConfig {
    DATABASE_CONF("config/database.properties"),
    STYTCH_CONF("config/stytch.properties"),
    CAT_SUGGEST("configcat-sdk-1/LxreCILqCUKPiPgevSQGoQ/w1WIJVMWoUOKocMj7FderA"),
    CAT_PROMOTION("configcat-sdk-1/LxreCILqCUKPiPgevSQGoQ/t9E7grtP10i2w1sJY3TJOQ"),
    CAT_PRICE_SUGEST("configcat-sdk-1/LxreCILqCUKPiPgevSQGoQ/BIIXGKL-k0ikTaH4FI7QSg"),
    JWT("config/jwt.properties");
    private String value;

    PathConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
