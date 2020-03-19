package dao;

import domain.Customer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import domain.Sale;
import java.util.ArrayList;
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
			sales.put("1", new Sale("1", "12/12/12"));
			sales.put("2", new Sale("2", "1/1/1"));
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
	 * Adds a account to the catalogue.
	 *
	 * @param item The sale being added.
	 */
	public void addSale(Sale sale) {
		sales.put(sale.getId(), sale);
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
	 * Updates an sale (effectively replaces it).
	 *
	 * @param id The ID of the sale to replace.
	 * @param updatedSale The sale to replace it with.
	 */
	public void updateSale(String id, Sale updatedSale) {
		sales.put(id, updatedSale);
	}

	/**
	 * Checks if an sale exists.
	 *
	 * @param id The ID of the sale being checked.
	 * @return <code>true</code> if product exists, <code>false</code> if not.
	 */
	public boolean exists(String id) {
		return sales.containsKey(id);
	}

}