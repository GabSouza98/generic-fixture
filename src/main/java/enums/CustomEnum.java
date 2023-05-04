package enums;

public enum CustomEnum {

    HIGH("highest"),
    MEDIUM("medium"),
    LOW("lowest");

    private String description;

    CustomEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
