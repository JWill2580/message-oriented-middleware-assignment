package service;

import dao.SalesDAO;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.apitool.ApiTool;
import org.jooby.handlers.Cors;
import org.jooby.handlers.CorsHandler;
import org.jooby.json.Gzon;
import resource.SalesResource;
import resource.SaleResource;

public class Server extends Jooby {

	public Server() {

		SalesDAO dao = new SalesDAO();

		use("*", new CorsHandler(new Cors().withMethods("*")));

		use(new Gzon());
		use(new SalesResource(dao));
		use(new SaleResource(dao));

		use(new ApiTool().swagger(new ApiTool.Options("/swagger").use("salesApi.yaml")));

	}

	public static void main(String[] args) throws IOException {

		Server server = new Server();

		CompletableFuture.runAsync(() -> {
			server.start();
		});

		server.onStarted(() -> {
			System.out.println("\nServer ready. Press Enter to stop service.");
		});

		System.in.read();
		System.exit(0);
	}

}
