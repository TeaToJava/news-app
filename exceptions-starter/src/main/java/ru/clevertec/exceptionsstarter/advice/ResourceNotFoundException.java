package ru.clevertec.exceptionsstarter.advice;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String objectType, String string) {
        super("Object" + objectType + " with Id " + string + " not found");
    }
}
