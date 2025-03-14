package br.com.alura.soundmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.apache.http.HttpException;

import java.io.IOException;

public class ConsultaArtista {

    public static String obterInformacoes(String nome) {

        Client client = Client.builder().apiKey(System.getenv("GEMINI_APIKEY")).build();
        GenerateContentResponse response;
        try {
            response = client.models.generateContent("gemini-2.0-flash-001", "De um pequeno texto que descreve quem seja esse artista/banda: " + nome, null);
        } catch (IOException | HttpException e) {
            throw new RuntimeException(e);
        }

        String resposta = response.text();
        return resposta;
    }

}
