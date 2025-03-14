package br.com.alura.soundmatch.repositorio;

import br.com.alura.soundmatch.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a WHERE a.id =  :id")
    Artista buscarArtista(Long id);
}
