package resource;

import dao.AccountsDAO;
import domain.ErrorMessage;
import domain.Account;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

public class AccountResource extends Jooby {

	public AccountResource(AccountsDAO dao) {

		path("/api/accounts", () -> {

			get(() -> {
				return dao.getAccounts();
			});

			post((req, rsp) -> {
				Account acc = req.body(Account.class);

				// construct the URI for this product
				String uri = "http://" + req.hostname() + ":" + req.port() + "" + req.path() + "/product/" + acc.getId();

				// tell the account about its URI
				acc.setUri(uri);

				if (!dao.exists(acc.getId())) {
					// store the product
					dao.addAccount(acc);

					// return a response containing the complete product
					rsp.status(Status.CREATED).send(acc);
				} else {
					// Non-unique ID
					rsp.status(Status.UNPROCESSABLE_ENTITY).send(new ErrorMessage("There is already a product with that ID."));
				}

			});

		}).produces(MediaType.json).consumes(MediaType.json);

	}

}
