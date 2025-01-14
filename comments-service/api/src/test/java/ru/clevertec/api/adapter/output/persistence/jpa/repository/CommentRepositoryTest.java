package ru.clevertec.api.adapter.output.persistence.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.api.DataConfig;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byDateGreaterThan;
import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byNewsId;
import static ru.clevertec.api.adapter.output.persistence.jpa.repository.CommentRepository.Specs.byUsername;

@Nested
@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {
    private final CommentRepository commentRepository;

    @Test
    void shouldFindAllBySpec() {
        LocalDateTime dateTime = LocalDateTime.of(2012, 12, 12, 12, 12);
        String username = "User 1";
        List<CommentEntity> results = commentRepository
                .findAll(byUsername(Optional.of(username))
                        .and(byDateGreaterThan(Optional.of(dateTime))));
        assertEquals(1, results.size());
    }

    @Test
    void shouldFindAllBySpecNotExist() {
        LocalDateTime dateTime = LocalDateTime.of(2012, 12, 12, 12, 12);
        List<CommentEntity> results = commentRepository
                .findAll(byUsername(Optional.empty())
                        .or(byDateGreaterThan(Optional.empty())));
        assertEquals(0, results.size());
    }

    @Test
    void shouldFindAllBySpecNewsId() {
        UUID newsId = UUID.fromString("b42f27ae-0bdb-4f41-b7d0-059a9b30513b");
        List<CommentEntity> results = commentRepository
                .findAll(byNewsId(newsId));
        assertEquals(7, results.size());
    }

    @Test
    void shouldFindByNewsId() {
        UUID newsId = UUID.fromString("b42f27ae-0bdb-4f41-b7d0-059a9b30513b");
        List<CommentEntity> results = commentRepository
                .findByNewsId(newsId);
        assertEquals(7, results.size());
    }

    @Test
    void findAllByNewsIdPageable() {
        UUID newsId = UUID.fromString("b42f27ae-0bdb-4f41-b7d0-059a9b30513b");
        List<CommentEntity> results = commentRepository
                .findAllByNewsId(newsId, PageRequest.of(0, 3)).stream().toList();
        assertEquals(3, results.size());
    }
}