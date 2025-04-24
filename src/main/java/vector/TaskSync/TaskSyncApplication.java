package vector.TaskSync;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import vector.TaskSync.auth.AuthenticationService;
import vector.TaskSync.auth.RegisterRequest;
import vector.TaskSync.models.Role;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TaskSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSyncApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstName("vieira")
					.lastName("ntwali")
					.email("vieira@gmail.com")
					.password("test@123")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());
			var manager = RegisterRequest.builder()
					.firstName("vieira")
					.lastName("ntwali")
					.email("manage@vieir.com")
					.password("test@123")
					.role(Role.MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}

}
