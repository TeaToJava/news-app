package ru.clevertec.core.exceptions;

public enum ResourceType {
    NEWS("News"),
    COMMENT("Comment");

    private String title;

    ResourceType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Type " + title;
    }
}
