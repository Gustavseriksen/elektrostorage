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
class OrderControllerTest {

    @Value("${local.server.port}")
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    // Tester at GET /orders returnerer 200 OK og en JSON-liste
    @Test
    void getAllOrders_returnerer200() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/orders"))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().startsWith("["));
    }

    // Tester at man ikke kan tilføje til en sendt bestilling — skal returnere 400
    @Test
    void addLineTilSendtOrdre_returnerer400() throws Exception {
        // Ordre 2 fra DataSeeder er sendt (har sendtDato sat)
        var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/orders/2/lines?componentId=1&antal=5"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }
}
