package ru.clevertec.api.adapter.input.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewsResponse {
    private UUID id;
    private LocalDateTime time;
    private String title;
    private String text;
}
