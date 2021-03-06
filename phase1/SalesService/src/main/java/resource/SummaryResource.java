package resource;

import dao.SalesDAO;
import domain.ErrorMessage;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

public class SummaryResource extends Jooby {

	public SummaryResource(SalesDAO dao) {

		path("/api/sales/summary/customer", () -> {
			
			// A route that sits at the top of the chain that checks that the ID
			// is valid so that the other routes don't need to.
			use("/:id", (req, rsp, chain) -> {
				String id = req.param("id").value();

				if (dao.exists(id)) {
					// ID is OK, so pass request on to the next route in the chain
					chain.next(req, rsp);
				} else {
					// Bad ID - send a 404 response
					rsp.status(Status.NOT_FOUND).send(new ErrorMessage("There is no customer matching that ID."));
				}
			});

			get("/:id", (req) -> {
				String id = req.param("id").value();
				return dao.getSummaryResource(id);
			});

		}).produces(MediaType.json).consumes(MediaType.json);

	}

}