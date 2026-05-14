package kea.elektrostorage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ComponentControllerTest {

    // Injicerer den tilfældige port som Spring starter testserveren på
    @Value("${local.server.port}")
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    // Tester at GET /components returnerer 200 OK og en JSON-liste
    @Test
    void getAllComponents_returnerer200() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/components"))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().startsWith("["));
    }

    // Tester at POST /components opretter en komponent og returnerer den
    @Test
    void createComponent_returnerer200OgData() throws Exception {
        String json = """
                {"internNummer": 999, "eksterntVarenummer": "EXT-TEST", "udgaaet": false, "skalSamles": false}
                """;

        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/components"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("999"));
    }
}
