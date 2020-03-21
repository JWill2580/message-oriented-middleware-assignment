package resource;

import dao.SalesDAO;
import domain.ErrorMessage;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

public class SalesResource extends Jooby {

	public SalesResource(SalesDAO dao) {

		path("/api/sales", () -> {

			get(() -> {
				return dao.getSales();
			});

			post((req, rsp) -> {
				Sale sale = req.body(Sale.class);

				if (!dao.exists(sale.getId())) {
					// store the account
					dao.addSale(sale);//DAO method invokation

					// return a response containing the complete account
					rsp.status(Status.CREATED).send(sale);
				} else {
					// Non-unique ID
					rsp.status(Status.UNPROCESSABLE_ENTITY).send(new ErrorMessage("There is already a sale with that ID."));
				}

			});

		}).produces(MediaType.json).consumes(MediaType.json);

	}

}