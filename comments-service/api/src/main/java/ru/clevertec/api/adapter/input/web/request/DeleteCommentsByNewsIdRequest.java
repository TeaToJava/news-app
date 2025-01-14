package ru.clevertec.api.adapter.input.web.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteCommentsByNewsIdRequest(@NotNull UUID newsId) {
}
