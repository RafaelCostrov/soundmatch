package br.com.alura.soundmatch;

import br.com.alura.soundmatch.principal.Principal;
import br.com.alura.soundmatch.repositorio.AlbumRepository;
import br.com.alura.soundmatch.repositorio.ArtistaRepository;
import br.com.alura.soundmatch.repositorio.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoundmatchApplication implements CommandLineRunner {

	private final Principal principal;

	@Autowired
	public SoundmatchApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(SoundmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.menu();
	}
}
