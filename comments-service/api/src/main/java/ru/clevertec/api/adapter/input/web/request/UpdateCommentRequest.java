package ru.clevertec.api.adapter.input.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(@NotBlank String text) {
}
