package br.com.alura.soundmatch.model.records;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSeguidores(@JsonAlias("href") String href,
                              @JsonAlias("total") int seguidores) {
}
