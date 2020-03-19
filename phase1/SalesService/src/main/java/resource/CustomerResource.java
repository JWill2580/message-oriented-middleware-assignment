/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import dao.SalesDAO;
import domain.ErrorMessage;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.MediaType;
import org.jooby.Status;

/**
 *
 * @author wiljo912
 */
public class CustomerResource {
public CustomerResource(SalesDAO dao) {

		path("/api/sales/customer", () -> {
			
			get(() -> {
				return dao.getSales();
			});
		}).produces(MediaType.json).consumes(MediaType.json);
	}
}