package br.com.alura.soundmatch.model.records;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosArtista(@JsonAlias("name") String nome,
                           @JsonAlias("popularity") int popularidade,
                           @JsonAlias("followers") DadosSeguidores seguidores,
                           @JsonAlias("genres") List<String> generos,
                           @JsonAlias("uri") String idArtista



) {
    @Override
    public String toString() {
        return "DadosArtista{" +
                "nome='" + nome + '\'' +
                ", popularidade=" + popularidade +
                ", seguidores=" + seguidores +
                ", generos=" + generos +
                '}';
    }
}
