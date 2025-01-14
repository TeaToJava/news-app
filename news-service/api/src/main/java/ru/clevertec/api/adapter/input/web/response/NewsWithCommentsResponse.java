package ru.clevertec.api.adapter.input.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.api.feignclient.model.CommentApiResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewsWithCommentsResponse {
    private UUID id;
    private LocalDateTime time;
    private String title;
    private String text;
    private List<CommentApiResponse> comments = new ArrayList<>();
}
