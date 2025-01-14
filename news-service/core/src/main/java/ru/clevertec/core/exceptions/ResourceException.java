package ru.clevertec.core.exceptions;

public class ResourceException extends RuntimeException {
    public ResourceException(String id) {

        super("News with " + id + "doesn't exist");
    }
}
