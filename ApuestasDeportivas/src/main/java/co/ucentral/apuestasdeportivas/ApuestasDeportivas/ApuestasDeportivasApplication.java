package co.ucentral.apuestasdeportivas.ApuestasDeportivas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios")
public class ApuestasDeportivasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApuestasDeportivasApplication.class, args);
	}
}