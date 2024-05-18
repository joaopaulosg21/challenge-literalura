package projeto.desafio.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BuscaApi {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String endereco, Class<T> object){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            return mapper.readValue(json,object);
        } catch (IOException | InterruptedException e ) {
            throw new RuntimeException(e);
        }
    }

}
