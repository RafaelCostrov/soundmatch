package br.com.alura.soundmatch.repositorio;

import br.com.alura.soundmatch.model.Album;
import br.com.alura.soundmatch.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
    @Query("SELECT m FROM Musica m WHERE m.artista.id = :id")
    List<Musica> musicasFiltradas(Long id);
}
