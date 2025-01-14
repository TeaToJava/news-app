package ru.clevertec.api.adapter.input.web.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(
                name = "comments-service",
                port = 8081
        )
})
@AutoConfigureMockMvc
public class NewsControllerWireMockTest {

    @InjectWireMock("comments-service")
    private WireMockServer wiremock;

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    void shouldReturnComment() {
        stubFor(get("/comments-service/comments/c4110185-7971-4d5f-aa98-790c5cdffa9f")
                .willReturn(okJson("""
                            {
                                "id": "c4110185-7971-4d5f-aa98-790c5cdffa9f",
                                "time": "2025-01-12T16:49:37.422957",
                                "text": "Comments",
                                "username": "Alina"
                            }
                        """)));
        assertThat(mockMvcTester.get()
                .uri("/news/{newsId}/comments/{commentId}",
                        UUID.fromString("3e6a4c5a-9d93-4679-a41e-25fa16168fd6"),
                        UUID.fromString("c4110185-7971-4d5f-aa98-790c5cdffa9f")))
                .hasStatusOk();
    }
}
