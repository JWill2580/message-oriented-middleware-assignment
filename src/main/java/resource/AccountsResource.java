package resource;

import dao.AccountsDAO;
import domain.ErrorMessage;
import domain.Account;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

public class AccountsResource extends Jooby {

	public AccountsResource(AccountsDAO dao) {

		path("/api/accounts/account", () -> {

			// A route that sits at the top of the chain that checks that the ID
			// is valid so that the other routes don't need to.
			use("/:id", (req, rsp, chain) -> {
				String id = req.param("id").value();

				if (dao.exists(id)) {
					// ID is OK, so pass request on to the next route in the chain
					chain.next(req, rsp);
				} else {
					// Bad ID - send a 404 response
					rsp.status(Status.NOT_FOUND).send(new ErrorMessage("There is no account matching that ID."));
				}
			});

			get("/:id", (req) -> {
				String id = req.param("id").value();
				return dao.getById(id);
			});

			delete("/:id", (req, rsp) -> {
				String id = req.param("id").value();
				dao.delete(id);
				rsp.status(Status.NO_CONTENT);
			});

			put("/:id", (req, rsp) -> {
				String id = req.param("id").value();
				Account item = req.body().to(Account.class);

				if (!id.equals(item.getId())) {
					rsp.status(Status.UNPROCESSABLE_ENTITY).send(new ErrorMessage("The product ID can not be modified via this operation."));
				} else {
					dao.updateAccount(id, item);
					rsp.status(Status.NO_CONTENT);
				}
			});

		}).produces(MediaType.json).consumes(MediaType.json);

	}

}
