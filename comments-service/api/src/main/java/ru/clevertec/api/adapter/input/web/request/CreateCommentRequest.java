package ru.clevertec.api.adapter.input.web.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCommentRequest(
        @NotBlank(message = "Text is required") String text,
        @NotBlank(message = "Username is required") String username,
        @NotNull(message = "NewsId is required") UUID newsId) {
}
