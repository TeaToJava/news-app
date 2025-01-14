package ru.clevertec.core.service;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.CreateNewsCommand;
import ru.clevertec.core.ports.out.CreateNewsPort;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateNewsServiceTest {
    @Mock
    CreateNewsPort createNewsPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;
    @InjectMocks
    CreateNewsService createNewsService;

    @Test
    void shouldCreateNews() {
       CreateNewsCommand createCommand = new CreateNewsCommand("Title", "Text");
        News news = News.builder().title("Title").text("Text").build();
        given(commandToDomainMapper.createNewsCommandToDomain(createCommand))
                .willReturn(news);
        doNothing().when(createNewsPort).saveNews(news);
        createNewsService.createNews(createCommand);
        verify(createNewsPort).saveNews(news);
    }
}