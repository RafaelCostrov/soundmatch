package br.com.alura.soundmatch.model.records;

import br.com.alura.soundmatch.model.wrappers.ArtistaWrapper;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaArtista(@JsonAlias("artists") ArtistaWrapper artistaWrapper) {
}
