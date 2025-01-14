package ru.clevertec.api.adapter.input.web.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteCommentRequest(@NotNull UUID id) {
}
