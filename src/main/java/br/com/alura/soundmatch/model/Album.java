package br.com.alura.soundmatch.model;


import br.com.alura.soundmatch.model.records.DadosAlbum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Entity
@Table(name = "albuns")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String idAlbum;
    private int numeroMusicas;
    private LocalDate dataLancamento;
    private String nome;

    @ManyToOne
    private Artista artista;
    @OneToMany (mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas;

    public Album(Long id, DadosAlbum dadosAlbum, Artista artista) {
        this.id = id;
        this.idAlbum = dadosAlbum.idAlbum();
        this.numeroMusicas = dadosAlbum.numeroMusicas();
        try {
            this.dataLancamento = LocalDate.parse(dadosAlbum.dataLancamento());
        } catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }
        this.nome = dadosAlbum.nome();
        this.artista = artista;
    }

    public Album() {}

    @Override
    public String toString() {
        return "Codigo: " + id + ", Album: " + nome + " do artista: " + artista.getNome() + ", lancado em : " + dataLancamento + " com " + numeroMusicas + " musicas";
    }

    public String getNome() {
        return nome;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public String getArtista() {
        return artista.getNome();
    }
}
