package ifmo.blss.entity;

public enum Kitchen {
    RUSSIAN("russian"),

    CHINESE("chinese"),
    ITALIAN("italian"),
    JAPAN("japan"),
    NONE("none");
    private final String name;

    Kitchen(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
