package ru.clevertec.api.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateNewsRequest(
        @NotBlank(message = "Title is required.") String title,
        @NotBlank(message = "Title is required.") String text) {
}
