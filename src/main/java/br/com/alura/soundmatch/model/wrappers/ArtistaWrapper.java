package br.com.alura.soundmatch.model.wrappers;

import br.com.alura.soundmatch.model.records.DadosArtista;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistaWrapper(@JsonAlias("items") List<DadosArtista> artistaList) {
    @Override
    public String toString() {
        return "ArtistaWrapper{" +
                "artistaList=" + artistaList +
                '}';
    }
}
