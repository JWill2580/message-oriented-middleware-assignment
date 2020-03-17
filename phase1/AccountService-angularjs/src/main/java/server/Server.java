package server;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.Results;

public class Server extends Jooby {

	public Server() {
		// allow all assets in the web resources folder to be requested
		assets("/**");
		
		// make index.html the root page
		assets("/", "index.html");
		
		// stop the log filling with 404 errors for favicon requests
		get("/favicon.ico", () -> Results.noContent());
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.port(8082);

		CompletableFuture.runAsync(() -> {
			server.start();
		});

		server.onStarted(() -> {
			System.out.println("\nServer ready.  Press Enter to stop.");
		});

		System.in.read();
		System.exit(0);
	}

}
