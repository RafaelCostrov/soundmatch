package br.com.alura.soundmatch.model;

import br.com.alura.soundmatch.model.records.DadosArtista;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private int popularidade;
    private int seguidores;
    private String genero;
    @Column(unique = true, nullable = false)
    private String idArtista;
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Album> albuns = new ArrayList<>();
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas = new ArrayList<>();


    public Artista(Long id, DadosArtista dadosArtista) {
        this.id = id;
        this.nome = dadosArtista.nome();
        this.popularidade = dadosArtista.popularidade();
        this.seguidores = dadosArtista.seguidores().seguidores();
        if (dadosArtista.generos() != null && !dadosArtista.generos().isEmpty()) {
            this.genero = dadosArtista.generos().get(0);
        } else {
            this.genero = "Desconhecido";
        }
        this.idArtista = dadosArtista.idArtista().split(":")[2];
    }

    public Artista() {}

    @Override
    public String toString() {
        return "Artista: " +
                "Codigo: " + id +
                ", Nome: " + nome +
                ", popularidade: " + popularidade +
                ", seguidores: " + seguidores +
                ", genero: " + genero;
    }

    public String getNome() {
        return nome;
    }

    public int getPopularidade() {
        return popularidade;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdArtista() {
        return idArtista;
    }
    public Long getId() {
        return id;
    }
}

