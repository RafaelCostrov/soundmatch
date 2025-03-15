package br.com.alura.soundmatch.principal;

import br.com.alura.soundmatch.model.Album;
import br.com.alura.soundmatch.model.Artista;
import br.com.alura.soundmatch.model.Musica;
import br.com.alura.soundmatch.model.records.DadosAlbum;
import br.com.alura.soundmatch.model.records.DadosMusica;
import br.com.alura.soundmatch.model.wrappers.AlbumWrapper;
import br.com.alura.soundmatch.model.wrappers.ArtistaWrapper;
import br.com.alura.soundmatch.model.records.DadosArtista;
import br.com.alura.soundmatch.model.records.RespostaArtista;
import br.com.alura.soundmatch.model.wrappers.MusicaWrapper;
import br.com.alura.soundmatch.repositorio.AlbumRepository;
import br.com.alura.soundmatch.repositorio.ArtistaRepository;
import br.com.alura.soundmatch.repositorio.MusicaRepository;
import br.com.alura.soundmatch.service.ConsomeApi;
import br.com.alura.soundmatch.service.ConsultaArtista;
import br.com.alura.soundmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    private final MusicaRepository musicaRepository;

    Scanner scanner = new Scanner(System.in);
    private ConsomeApi consumo = new ConsomeApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://api.spotify.com/v1";
    private List<Artista> artistas = new ArrayList<>();
    private List<Album> albuns = new ArrayList<>();
    private List<Musica> musicas = new ArrayList<>();
    private Artista artistaBuscado = null;

    @Autowired
    public Principal(ArtistaRepository artistaRepository, AlbumRepository albumRepository, MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
        this.musicaRepository = musicaRepository;
    }

    public void menu() {
        String escolha = "";
        while (!escolha.equals("0")) {
            String menu = """
                    *******************************************************************
                    Bem vindo ao SoundMatch, 
                                    
                    Por favor, escolha uma das opcoes abaixo para continuar:
                    1 - Cadastrar artista;
                    2 - Listar artistas;
                    3 - Cadastrar album por artista;
                    4 - Listar albuns;
                    5 - Cadastrar musicas por album;
                    6 - Listar musicas;
                    7 - Listar musicas por artista;
                    8 - Pesquisar dados sobre um artista;                
                                    
                                    
                                    
                    0 - Sair
                    """;
            System.out.println(menu);
            escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    cadastrarArtista();
                    break;
                case "2":
                    listarArtistas();
                    break;
                case "3":
                    cadastrarAlbum();
                    break;
                case "4":
                    listarAlbuns();
                    break;
                case "5":
                    cadastrarMusicas();
                    break;
                case "6":
                    listarMusicas();
                    break;
                case "7":
                    listarMusicasPorArtista();
                    break;
                case "8":
                    pesquisarArtista();
                    break;

                case "0":
                    System.out.println("Ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    menu();
            }
        }
    }

    private void cadastrarArtista() {
        System.out.println("Qual artista deseja buscar?");
        String artistaBuscado = scanner.nextLine();
        String json = consumo.obterDados(ENDERECO + "/search?q=" + artistaBuscado.replace(" ", "%20") + "&type=artist&limit=1");
        RespostaArtista artistas = conversor.converterDados(json, RespostaArtista.class);
        ArtistaWrapper artistaWrapper = artistas.artistaWrapper();
        if (!artistaWrapper.artistaList().isEmpty()) {
            DadosArtista dadosArtista = artistaWrapper.artistaList().get(0);
            Artista artista = new Artista(null, dadosArtista);
            try {
                artistaRepository.save(artista);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Já existe um artista com o nome '" + artista.getNome() + "'.");
            }
            System.out.printf("Artista cadastrado: %s - Genero: %s - Popularidade: %s - Numero de Seguidores: %s\n",
                    artista.getNome(), artista.getGenero(), artista.getPopularidade(), artista.getSeguidores());

        } else {
            System.out.println(artistaBuscado + " nao encontrado!");
        }
    }


    private void listarArtistas() {
        artistas = artistaRepository.findAll();
        artistas.stream()
                .sorted(Comparator.comparing(Artista::getNome))
                .forEach(System.out::println);
    }


    private void cadastrarAlbum() {
        System.out.println("Escolha um dos artistas abaixo para cadastrar seus albuns: (Escolha pelo codigo)");
        escolherArtista();
        String id = artistaBuscado.getIdArtista();
        String json = consumo.obterDados(ENDERECO + "/artists/" + id + "/albums?include_groups=album");
        AlbumWrapper albuns = conversor.converterDados(json, AlbumWrapper.class);
        List<DadosAlbum> dadosAlbumList = albuns.dadosAlbuns();
        Artista finalArtistaBuscado = artistaBuscado;
        List<Album> albumList = dadosAlbumList.stream()
                .map(d -> {
                    Album album = new Album(null, d, finalArtistaBuscado);
                    try {
                        albumRepository.save(album);
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("Erro ao salvar o álbum '" + album.getNome() + "': " + e.getMessage());
                    }
                    return album;
                })
                .filter(Objects::nonNull)
                .toList();
    }


    private void listarAlbuns() {
        albuns = albumRepository.findAll();
        albuns.stream()
                .sorted(Comparator.comparing(Album::getNome))
                .forEach(System.out::println);
    }

    private void escolherArtista() {
        listarArtistas();
        boolean entradaValida = false;
        Long artista = null;
        while (!entradaValida) {
            try {
                    artista = scanner.nextLong();
                    entradaValida = true;

            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida! Escolha um codigo valido: ");
                listarArtistas();
            } finally {
                scanner.nextLine();
            }
        }
        try {
            artistaBuscado = artistaRepository.buscarArtista(artista);
            if (artistaBuscado == null) {
                throw new IllegalArgumentException("Artista não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
    }


    private void cadastrarMusicas() {
        System.out.println("Escolha um dos artistas abaixo para cadastrar suas musicas: (Escolha pelo codigo)");
        escolherArtista();
        Long id = artistaBuscado.getId();
        List<Album> albunsArtista = albumRepository.albunsFiltrados(id);
        albunsArtista.stream()
                .sorted(Comparator.comparing(Album::getArtista))
                .forEach(System.out::println);
        System.out.println("Qual album acima voce deseja cadastrar? (Escolha o codigo)");
        Long idAlbum = scanner.nextLong();
        Album albumEscolhido = albumRepository.buscarAlbum(idAlbum);
        String json = consumo.obterDados(ENDERECO + "/albums/" + albumEscolhido.getIdAlbum() + "/tracks");
        MusicaWrapper musicaWrapper = conversor.converterDados(json, MusicaWrapper.class);
        List<DadosMusica> dadosMusicas = musicaWrapper.dadosMusicas();
        List<Musica> musicas = dadosMusicas.stream()
                .map(m -> {
                    Musica musica = new Musica(null, m, albumEscolhido, artistaBuscado);
                    try {
                        musicaRepository.save(musica);
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("Erro ao salvar a musica '" + musica.getNome() + "': " + e.getMessage());
                    }
                    return musica;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private void listarMusicas() {
        musicas = musicaRepository.findAll();
        musicas.stream()
                .sorted(Comparator.comparing(Musica::getArtista))
                .forEach(System.out::println);
    }


    private void listarMusicasPorArtista() {
        System.out.println("Escolha um dos artistas abaixo para listar suas musicas: (Escolha pelo codigo)");
        escolherArtista();
        Long id = artistaBuscado.getId();
        List<Musica> musicas = musicaRepository.musicasFiltradas(id);
        musicas.forEach(System.out::println);
    }


    private void pesquisarArtista() {
        System.out.println("Escolha um dos artistas abaixo para pesquisar informacoes a seu respeito: (Escolha pelo codigo)");
        escolherArtista();
        String nome = artistaBuscado.getNome();
        String texto = ConsultaArtista.obterInformacoes(nome);
        System.out.println(texto);
    }
}
