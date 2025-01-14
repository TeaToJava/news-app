package ru.clevertec.api.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.api.feignclient.model.CommentApiResponse;
import ru.clevertec.api.feignclient.model.DeleteCommentsRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "comments-service")
public interface CommentsFeignClient {
    @GetMapping("/comments/{commentId}")
    CommentApiResponse findComment(@PathVariable("commentId") UUID commentId);

    @GetMapping("/comments")
    List<CommentApiResponse> getCommentsByNewsId(@RequestParam("newsId") UUID newsId,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size);


    @PostMapping("/comments")
    void deleteCommentsByNewsId(@RequestBody DeleteCommentsRequest deleteCommentsRequest);
}
