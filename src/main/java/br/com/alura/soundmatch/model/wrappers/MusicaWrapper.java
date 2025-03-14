package br.com.alura.soundmatch.model.wrappers;

import br.com.alura.soundmatch.model.records.DadosMusica;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MusicaWrapper(@JsonAlias("items")List<DadosMusica> dadosMusicas) {
}
