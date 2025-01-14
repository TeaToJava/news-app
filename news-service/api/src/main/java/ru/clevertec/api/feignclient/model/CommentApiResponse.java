package ru.clevertec.api.feignclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentApiResponse {
    private UUID id;
    private LocalDateTime time;
    private String text;
    private String username;
}
