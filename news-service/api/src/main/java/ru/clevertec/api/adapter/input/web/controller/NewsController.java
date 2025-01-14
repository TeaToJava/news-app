package ru.clevertec.api.adapter.input.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.api.adapter.input.web.mapper.CommentMapper;
import ru.clevertec.api.adapter.input.web.mapper.NewsMapper;
import ru.clevertec.api.adapter.input.web.request.CreateNewsRequest;
import ru.clevertec.api.adapter.input.web.request.UpdateNewsRequest;
import ru.clevertec.api.adapter.input.web.response.CommentResponse;
import ru.clevertec.api.adapter.input.web.response.NewsResponse;
import ru.clevertec.api.adapter.input.web.response.NewsWithCommentsResponse;
import ru.clevertec.api.feignclient.model.CommentApiResponse;
import ru.clevertec.core.model.Comment;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.CreateNewsUseCase;
import ru.clevertec.core.ports.in.DeleteNewsUseCase;
import ru.clevertec.core.ports.in.ReadNewsUseCase;
import ru.clevertec.core.ports.in.UpdateNewsUseCase;
import ru.clevertec.core.ports.in.command.DeleteNewsCommand;
import ru.clevertec.core.ports.in.command.ReadNewsCommand;
import ru.clevertec.core.ports.in.command.ReadNewsCommandWithAllComments;
import ru.clevertec.core.ports.in.command.ReadNewsCommentCommand;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@Validated
@RequiredArgsConstructor
public class NewsController {

    private final CreateNewsUseCase createNewsUseCase;
    private final ReadNewsUseCase readNewsUseCase;
    private final UpdateNewsUseCase updateNewsUseCase;
    private final DeleteNewsUseCase deleteNewsUseCase;
    private final NewsMapper newsMapper;
    private final CommentMapper commentMapper;

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponse> getNews(@PathVariable("newsId") @NotNull UUID newsId) {
        return ResponseEntity.ok(newsMapper.toNewsResponse(readNewsUseCase.findNewsById(new ReadNewsCommand(newsId))));
    }

    @GetMapping()
    public ResponseEntity<List<NewsResponse>> getAllNews(@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(newsMapper.toNewsResponses(readNewsUseCase.findAllNewsByPage(page, size)));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<NewsResponse>> findNews(@RequestParam("date")
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       Optional<LocalDate> date,
                                                       @RequestParam("title") Optional<String> title) {
        return ResponseEntity.ok(newsMapper.toNewsResponses(readNewsUseCase.findByTitleAndDate(date, title)));
    }


    @PostMapping()
    public ResponseEntity<Void> createNews(@RequestBody @Valid CreateNewsRequest createNewsRequest) {
        createNewsUseCase.createNews(newsMapper.toCreateNewsCommand(createNewsRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{newsId}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable("newsId") @NotNull UUID id,
                                                   @RequestBody @Valid UpdateNewsRequest updateNewsRequest) {
        News news = updateNewsUseCase.updateNews(id, newsMapper.toUpdateNewsCommand(updateNewsRequest));
        return ResponseEntity.ok(newsMapper.toNewsResponse(news));
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable("newsId") @Valid UUID id) {
        deleteNewsUseCase.deleteNews(new DeleteNewsCommand(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{newsId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> findNewsComment(@PathVariable("newsId") @NotNull UUID newsId,
                                                           @PathVariable("commentId") @NotNull UUID commentId) {
        Comment comment = readNewsUseCase.findNewsComment(new ReadNewsCommentCommand(newsId, commentId));
        return ResponseEntity.ok(commentMapper.toCommentResponse(comment));
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<NewsWithCommentsResponse> findNewsWithComments(@PathVariable("newsId") @Valid UUID newsId,
                                                                         @RequestParam(name = "page", required = false,
                                                                                 defaultValue = "0") int page,
                                                                         @RequestParam(name = "size",
                                                                                 required = false,
                                                                                 defaultValue = "10") int size) {
        News news = readNewsUseCase.findNewsByIdWithComments(new ReadNewsCommandWithAllComments(newsId, page, size));
        return ResponseEntity.ok(newsMapper.toNewsWithCommentsResponse(news));
    }

}