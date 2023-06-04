package ifmo.blss.entity;


public enum Role {
    USER("USER"),
    ADMIN("ADMIN");
    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

};
