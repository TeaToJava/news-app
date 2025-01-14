package ru.clevertec.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.core.exceptions.ResourceException;
import ru.clevertec.core.model.CommandToDomainMapper;
import ru.clevertec.core.model.News;
import ru.clevertec.core.ports.in.command.UpdateNewsCommand;
import ru.clevertec.core.ports.out.ReadNewsPort;
import ru.clevertec.core.ports.out.UpdateNewsPort;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateNewsServiceTest {
    @Mock
    UpdateNewsPort updateNewsPort;
    @Mock
    CommandToDomainMapper commandToDomainMapper;

    @Mock
    ReadNewsPort readNewsPort;
    @InjectMocks
    UpdateNewsService updateNewsService;

    @Test
    void shouldUpdateNewsWhenNewsExist() {
        UpdateNewsCommand updateCommand = new UpdateNewsCommand("Title edition", "Text edition");
        UUID id = UUID.randomUUID();
        News news = News.builder().title("Title").id(id).text("Text").build();
        News actual = News.builder().title("Title edition").id(id).text("Text edition").build();

        given(readNewsPort.getNewsById(id)).willReturn(Optional.of(news));
        given(updateNewsPort.updateNews(news)).willReturn(actual);
        News expected = updateNewsService.updateNews(id, updateCommand);

        assertTrue(actual.equals(expected));
    }

    @Test
    void shouldThrowExceptionWhenNewsNotExist() {
        UpdateNewsCommand updateCommand = new UpdateNewsCommand("Title edition", "Text edition");
        UUID id = UUID.randomUUID();
        ResourceException ex = new ResourceException(id.toString());

        given(readNewsPort.getNewsById(id)).willThrow(ex);

        assertThrows(ResourceException.class, () -> updateNewsService.updateNews(id, updateCommand));
        assertEquals("News with " + id + "doesn't exist", ex.getMessage());
    }
}