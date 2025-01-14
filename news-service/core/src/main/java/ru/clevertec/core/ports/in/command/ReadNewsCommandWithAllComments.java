package ru.clevertec.core.ports.in.command;

import java.util.UUID;

public record ReadNewsCommandWithAllComments(UUID id, int page, int size) {
}

