package ru.clevertec.api.adapter.output.persistence;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;
import ru.clevertec.api.adapter.output.persistence.jpa.repository.NewsRepository;
import ru.clevertec.api.adapter.output.persistence.jpa.specs.NewsSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.clevertec.api.adapter.output.persistence.jpa.specs.NewsSpecification.hasDateGreaterThan;
import static ru.clevertec.api.adapter.output.persistence.jpa.specs.NewsSpecification.hasTitle;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:db-test/data.sql")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class NewsRepositoryTest {

    private final NewsRepository newsRepository;

    @Test
    public void shouldReturnNews_whenTitleExist() {
        String itNews = "IT-news";
        List<NewsEntity> results = newsRepository
                .findAll(hasTitle(Optional.of(itNews)));
        assertEquals(1, results.size());
        assertTrue(results.stream().allMatch(news -> news.getTitle().equals(itNews)));
    }

    @Test
    public void shouldReturnNews_whenDateGreaterThanExist() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2012, 10, 5), LocalTime.MIN);
        List<NewsEntity> results = newsRepository.findAll(hasDateGreaterThan(Optional.of(dateTime)));
        assertTrue(!results.isEmpty());
    }

    @Test
    public void shouldReturnNews_whenDateGreaterThanExistAndTitleExist() {
        String itNews = "IT-news";
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(2012, 10, 5), LocalTime.MIN);
        List<NewsEntity> results =
                newsRepository
                        .findAll(hasDateGreaterThan(Optional.of(dateTime))
                                .and(hasTitle(Optional.of(itNews))));
        assertTrue(!results.isEmpty());
    }
}