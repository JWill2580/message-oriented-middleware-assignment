package server;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.Results;
import org.jooby.handlers.Cors;
import org.jooby.handlers.CorsHandler;

public class Server extends Jooby {

	public Server() {
                use("*", new CorsHandler(new Cors().withMethods("*")));
		assets("/**");
		assets("/", "index.html");
		get("/favicon.ico", () -> Results.noContent());
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();

		server.port(7081);

		CompletableFuture.runAsync(() -> {
			server.start();
		});

		server.onStarted(() -> {
			System.out.println("\nPress Enter to stop service.");
		});

		System.in.read();
		System.exit(0);
	}

}
