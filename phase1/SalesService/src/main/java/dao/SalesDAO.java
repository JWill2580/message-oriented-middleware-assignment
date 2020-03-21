package dao;

import domain.Customer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import domain.Sale;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SalesDAO {

	private static final Map<String, Sale> sales = new HashMap<>();
	private static final Multimap<String, Sale> salesMM = ArrayListMultimap.create();
	/*
	 * Some dummy data for testing
	 */
	static {
		if (sales.isEmpty()) {
			Customer cust1 = new Customer("1", "Josh@gamil.com", "low");
			Customer cust2 = new Customer("2", "Ruby@gamil.com", "high");
			Sale sale1 = new Sale("1", "12/12/12", cust1);
			Sale sale2 = new Sale("2", "1/1/1", cust1);

			sales.put(sale1.getId(), sale1);
			sales.put(sale2.getId(), sale2);
			salesMM.put(cust1.getId(), sale1);
			salesMM.put(cust1.getId(), sale2);
		}
	}

	/**
	 * Gets all sale in the catalogue ordered by ID.
	 *
	 * @return All sale ordered by ID.
	 */
	public List<Sale> getSales() {
		return new ArrayList<>(sales.values());
	}

	
	/**
	 * Get a sale through the customer id
	 * 
	 * @param id the id of the customer
	 * @return the sale with that id
	 */
	public List<Sale> getThroughId(String id){
		return new ArrayList<>(salesMM.get(id));
	}
	
	/**
	 * Adds a account to the catalogue.
	 *
	 * @param item The sale being added.
	 */
	public void addSale(Sale sale) {
		sales.put(sale.getId(), sale);
		salesMM.put(sale.getCustomer().getId(), sale);
	}

	/**
	 * Gets a single sale that matches the given ID.
	 *
	 * @param id The ID to search for.
	 * @return The sale matching the given ID, or null if no match found.
	 */
	public Sale getById(String id) {
		return sales.get(id);
	} 

	/**
	 * Deletes a sale.
	 *
	 * @param id The ID of the sale to delete.
	 */
	public void delete(String id) {
		sales.remove(id);
	}

	/**
	 * Checks if an sale exists.
	 *
	 * @param id The ID of the sale being checked.
	 * @return <code>true</code> if product exists, <code>false</code> if not.
	 */
	public boolean exists(String id) {
		return salesMM.containsKey(id);
	}
	
	/*public static void main(String[] args) {
		SalesDAO dao = new SalesDAO();
		
		System.out.println(dao.getThroughId("1"));
		System.out.println(dao.exists("1"));
	}*/
}