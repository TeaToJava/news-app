package ru.clevertec.api.adapter.input.web.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import ru.clevertec.api.adapter.input.web.mapper.CommentMapper;
import ru.clevertec.api.adapter.input.web.mapper.NewsMapper;
import ru.clevertec.api.adapter.input.web.response.NewsResponse;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.CreateNewsUseCase;
import ru.clevertec.core.ports.in.DeleteNewsUseCase;
import ru.clevertec.core.ports.in.ReadNewsUseCase;
import ru.clevertec.core.ports.in.UpdateNewsUseCase;
import ru.clevertec.core.ports.in.command.ReadNewsCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;
    @MockitoBean
    private CreateNewsUseCase createNewsService;
    @MockitoBean
    private ReadNewsUseCase readNewsService;
    @MockitoBean
    private UpdateNewsUseCase updateNewsService;
    @MockitoBean
    private DeleteNewsUseCase deleteNewsService;
    @MockitoBean
    private NewsMapper newsMapper;
    @MockitoBean
    private CommentMapper commentMapper;

    private static final LocalDateTime DATE_TIME = LocalDateTime.now();

    @Test
    void shouldGetNews() {
        UUID newsId = UUID.randomUUID();
        News news = getNews().getFirst();
        NewsResponse newsResponse = getNewsResponses().getFirst();
        given(this.readNewsService.findNewsById(new ReadNewsCommand(newsId)))
                .willReturn(news);
        given(newsMapper.toNewsResponse(news)).willReturn(newsResponse);

        assertThat(mockMvcTester.get()
                .uri("/news/{id}", newsId))
                .hasStatusOk();
    }

    @Test
    void shouldGetAllNews() {
        int page = 0;
        int size = 5;
        List<News> news = getNews();
        List<NewsResponse> newsResponses = getNewsResponses();
        given(this.readNewsService.findAllNewsByPage(page, size))
                .willReturn(news);
        given(newsMapper.toNewsResponses(news)).willReturn(newsResponses);
        MvcTestResult result = mockMvcTester.get()
                .uri("/news")
                .param("page", "0")
                .param("size", "5").exchange();
        assertThat(result).hasStatusOk();
    }

    private List<News> getNews() {
        News news = News.builder()
                .id(UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452"))
                .time(DATE_TIME)
                .title("News Title 1")
                .text("News 1").build();
        News newsTwo = News.builder()
                .id(UUID.fromString("f5ad450b-5d92-4cbf-8393-96a3ee4a3f00"))
                .time(DATE_TIME)
                .title("News Title 2")
                .text("News 2").build();
        return List.of(news, newsTwo);
    }

    private List<NewsResponse> getNewsResponses() {
        NewsResponse first = NewsResponse.builder()
                .id(UUID.fromString("8d10be5a-8b7b-43e7-b7a0-dd6b92619452"))
                .time(DATE_TIME)
                .title("News Title 1")
                .text("News 1").build();
        NewsResponse second = NewsResponse.builder()
                .id(UUID.fromString("f5ad450b-5d92-4cbf-8393-96a3ee4a3f00"))
                .time(DATE_TIME)
                .title("News Title 2")
                .text("News 2").build();
        return List.of(first, second);
    }
}