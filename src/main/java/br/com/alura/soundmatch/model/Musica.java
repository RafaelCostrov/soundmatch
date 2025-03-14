package br.com.alura.soundmatch.model;

import br.com.alura.soundmatch.model.records.DadosMusica;
import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tempoMusica;
    private boolean explicita;
    @Column(unique = true, nullable = false)
    private String idMusica;
    private String nome;
    @ManyToOne
    private Artista artista;
    @ManyToOne
    private Album album;

    public Musica(Long id, DadosMusica dadosMusica, Album album, Artista artista) {
        this.id = id;
        int tempoEmMilissegundos = dadosMusica.tempoMusica();
        int totalSegundos = tempoEmMilissegundos / 1000;
        int minutos = totalSegundos / 60;
        int segundos = totalSegundos % 60;
        this.tempoMusica = String.format("%02d:%02d", minutos, segundos);
        this.explicita = dadosMusica.explicita();
        this.idMusica = dadosMusica.idMusica();
        this.nome = dadosMusica.nome();
        this.artista = artista;
        this.album = album;
    }

    public Musica() {}

    @Override
    public String toString() {
        return "Codigo: " + id + ", nome da musica: " + nome + ", tempo da musica: " + tempoMusica + " do artista: " + artista.getNome() + " e do album " + album.getNome()
                + " (Explicita: " + isExplicitaFormatado() + ")";
    }

    public Long getId() {
        return id;
    }

    public String getTempoMusica() {
        return tempoMusica;
    }

    public boolean isExplicita() {
        return explicita;
    }

    public String getIdMusica() {
        return idMusica;
    }

    public String getNome() {
        return nome;
    }

    public String getArtista() {
        return artista.getNome();
    }

    public Album getAlbum() {
        return album;
    }

    public String isExplicitaFormatado() {
        return explicita ? "Sim" : "Não";
    }
}
