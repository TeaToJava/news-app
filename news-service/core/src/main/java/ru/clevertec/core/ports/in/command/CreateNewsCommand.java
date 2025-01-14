package ru.clevertec.core.ports.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNewsCommand {
    private String title;
    private String text;
}
