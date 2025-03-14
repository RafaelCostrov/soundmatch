package br.com.alura.soundmatch.repositorio;

import br.com.alura.soundmatch.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.artista.id = :id")
    List<Album> albunsFiltrados(Long id);

    @Query("SELECT a FROM Album a WHERE a.id = :id")

    Album buscarAlbum(Long id);

}
