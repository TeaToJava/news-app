package ru.clevertec.api.adapter.input.web.request;


import jakarta.validation.constraints.NotBlank;

public record CreateNewsRequest(
        @NotBlank(message = "Title is required.") String title,
        @NotBlank(message = "Text is required.") String text) {
}
