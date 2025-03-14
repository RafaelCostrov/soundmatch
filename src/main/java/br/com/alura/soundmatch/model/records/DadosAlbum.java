package br.com.alura.soundmatch.model.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAlbum(@JsonAlias("id") String idAlbum,
                         @JsonAlias("total_tracks") int numeroMusicas,
                         @JsonAlias("release_date") String dataLancamento,
                         @JsonAlias("name") String nome
                         ) {
}
