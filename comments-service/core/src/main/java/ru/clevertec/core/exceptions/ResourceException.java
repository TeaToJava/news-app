package ru.clevertec.core.exceptions;

public class ResourceException extends RuntimeException {
    public ResourceException(ResourceType type, String id) {
        super(type + " " + id + "doesn't exist");
    }
}
