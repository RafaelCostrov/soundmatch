package br.com.alura.soundmatch.model.wrappers;

import br.com.alura.soundmatch.model.records.DadosAlbum;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumWrapper(@JsonAlias("items") List<DadosAlbum> dadosAlbuns) {
}
