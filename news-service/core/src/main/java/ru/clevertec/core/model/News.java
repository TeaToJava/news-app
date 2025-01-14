package ru.clevertec.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News implements Serializable {
    private UUID id;
    private LocalDateTime time;
    private String title;
    private String text;
    private List<Comment> comments = new ArrayList<>();
}
