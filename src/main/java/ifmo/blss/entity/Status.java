package ifmo.blss.entity;

public enum Status {
    MODERATION("MODERATION"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");
    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
