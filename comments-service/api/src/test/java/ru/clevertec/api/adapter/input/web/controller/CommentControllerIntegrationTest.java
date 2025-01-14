package ru.clevertec.api.adapter.input.web.controller;


import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc

public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvcTester mockMvcTester;


    @Test
    void shouldThrowErrorWhenGetCommentById() {
        assertThat(mockMvcTester.get()
                .uri("/comments/{id}", UUID.randomUUID()))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("No value present");
    }


    @Test
    void shouldThrowErrorWhenGetAllComment() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/comments")
                .param("page", "0.8")
                .param("size", "5").exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }


   @Test
    void shouldThrowErrorWhenCreateComment() {
        MvcTestResult result = mockMvcTester.post()
                .uri("/comments").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "text": "Test comment"
                                      }
                                """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrowErrorWhenUpdateComment() {
        MvcTestResult result = mockMvcTester.put()
                .uri("/comments/{id}",
                        UUID.fromString("825974bb-4ad1-4639-9bc2-abbdd9aa6ca8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "text": "Test comment"
                                      }
                                """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrowErrorWhenDeleteComment() {
        MvcTestResult result = mockMvcTester.delete()
                .uri("/comments/{commentId}", UUID.randomUUID())
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

}