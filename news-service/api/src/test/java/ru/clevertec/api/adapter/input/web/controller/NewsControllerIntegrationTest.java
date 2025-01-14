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

public class NewsControllerIntegrationTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    void shouldGetNewsById() {
        assertThat(mockMvcTester.get()
                .uri("/news/{id}",
                        UUID.fromString("825974bb-4ad1-4639-9bc2-abbdd9aa6ca8")))
                .hasStatusOk();
    }

    @Test
    void shouldThrowErrorWhenGetNewsById() {
        assertThat(mockMvcTester.get()
                .uri("/news/{id}", UUID.randomUUID()))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.message").isEqualTo("No value present");
    }

    @Test
    void shouldGetAllNews() {
        assertThat(mockMvcTester.get()
                .uri("/news"))
                .hasStatusOk();
    }

    @Test
    void shouldThrowErrorWhenGetAllNews() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/news")
                .param("page", "0.8")
                .param("size", "5").exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldFindNewsByFilter() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/news/filter")
                .param("title", "Lloyds Office")
                .exchange();
        assertThat(result).hasStatusOk();
    }

    @Test
    @SneakyThrows
    void shouldCreateNews() {
        MvcTestResult result = mockMvcTester.post()
                .uri("/news").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                       {
                                           "title": "News 1",
                                           "text": "News 2"
                                       }
                                """)
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.valueOf(201));
    }

    @Test
    void shouldThrowErrorWhenCreateNews() {
        MvcTestResult result = mockMvcTester.post()
                .uri("/news").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "title": "Test news"
                                      }
                                """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrowErrorWhenUpdateNews() {
        MvcTestResult result = mockMvcTester.put()
                .uri("/news/{id}",
                        UUID.fromString("825974bb-4ad1-4639-9bc2-abbdd9aa6ca8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "title": "Test news"
                                      }
                                """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @SneakyThrows
    void shouldUpdateNews() {
        UUID newsId = UUID.fromString("825974bb-4ad1-4639-9bc2-abbdd9aa6ca8");
        MvcTestResult result = mockMvcTester.put()
                .uri("/news/{id}", newsId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                       {
                                           "title": "Test news",
                                           "text": "Test text"
                                       }
                                """)
                .exchange();

        assertThat(result).hasStatusOk();
        String json = result.getResponse().getContentAsString();
        assertThat((JsonPath.read(json, "title")).equals("Test news"));
        assertThat((JsonPath.read(json, "text")).equals("Test text"));
        assertThat((JsonPath.read(json, "id")).equals(newsId));
    }

    @Test
    void shouldThrowErrorWhenDeleteNews() {
        MvcTestResult result = mockMvcTester.delete()
                .uri("/news/{newsId}", UUID.randomUUID())
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }


}