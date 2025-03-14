package br.com.alura.soundmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ObterAuth {
    private static final String CLIENT_ID = System.getenv("ID_SPOTIFY");
    private static final String CLIENT_SECRET = System.getenv("SECRET_SPOTIFY");
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    public static String obterToken() {
        HttpClient client = HttpClient.newHttpClient();
        String authHeader = CLIENT_ID + ":" + CLIENT_SECRET;
        String encodedAuth = Base64.getEncoder().encodeToString(authHeader.getBytes());
        String formData = "grant_type=client_credentials";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_URL))
                .header("Authorization", "Basic " + encodedAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();
        HttpResponse<String> response = null;
    try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    String json = response.body();
    return extrairToken(json);
    }

    private static String extrairToken(String json) {
        return json.split("\"access_token\":\"")[1].split("\"")[0];
    }

}
