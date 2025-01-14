package ru.clevertec.api.feignclient.model;

import jakarta.validation.Valid;

import java.util.UUID;

public record DeleteCommentsRequest(@Valid UUID newsId) {
}
