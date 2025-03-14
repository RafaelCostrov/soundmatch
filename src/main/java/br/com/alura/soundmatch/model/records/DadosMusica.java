package br.com.alura.soundmatch.model.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosMusica(@JsonAlias("duration_ms") int tempoMusica,
                          @JsonAlias("explicit") boolean explicita,
                          @JsonAlias("id") String idMusica,
                          @JsonAlias("name") String nome){
}
