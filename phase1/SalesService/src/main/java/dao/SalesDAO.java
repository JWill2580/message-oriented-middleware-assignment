package dao;

import domain.Customer;
import domain.Totals;
import domain.SaleItem;
import domain.Summary;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import domain.Sale;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesDAO {

	private static final Map<String, Sale> sales = new HashMap<>();
	private static final ListMultimap<String, Sale> salesMM = ArrayListMultimap.create();
	/*
	 * Some dummy data for testing
	 */
	static {
		if (sales.isEmpty()) {
			Customer cust1 = new Customer("WD1321", "Josh@gmail.com", "low");
			Totals total = new Totals(4.0, 1.0);

			SaleItem item = new SaleItem("1", 1.0, 4.0);

			//Customer cust2 = new Customer("2", "Ruby@gamil.com", "high");
			Sale sale1 = new Sale("1", "12/12/12", cust1, total, item);
			Sale sale2 = new Sale("2", "1/1/1", cust1, total, item);
			//sale1.addSaleItem(item);
			//sale2.addSaleItem(item);

			sales.put(sale1.getId(), sale1);
			sales.put(sale2.getId(), sale2);
			salesMM.put(sale1.getCustomer().getId(), sale1);
			salesMM.put(sale2.getCustomer().getId(), sale2);
		}
	}
	
	/**
	 * Calculating the summary resource
	 * @param id the customers sales
	 * @return the summary of the sales for that customer
	 */
	public Summary getSummaryResource(String id){
		Summary sum = new Summary();
		
		//Setting numOfSales of number of separate values in MultiMap
		int numOfSales = salesMM.get(id).size();
		sum.setNumberOfSales(numOfSales);
		
		//Setting total to sum of payment
		double totalPayment = 0.0;
		
		for(Sale sale : salesMM.get(id)){
			totalPayment += sale.getTotal().getTotalPayment();
		}
		sum.setTotalPayment(totalPayment);
		
		//Setting the group
		if(totalPayment > 5000){
			sum.setGroup("high");
		} else {
			sum.setGroup("low");
		}
		//setting URI
		sum.setUri("http://localhost:8080/api/sales/summary/customer/" + id);
		return sum;
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
		return salesMM.get(id);
	}
	
	/**
	 * Adds a account to the catalogue.
	 *
	 * @param sale The sale being added.
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
		salesMM.removeAll(id);
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
	//Main method used for testing
	/*public static void main(String[] args) {
		SalesDAO dao = new SalesDAO();
		
		//System.out.println(dao.getThroughId("1"));
		//System.out.println(dao.exists("WD1321"));
		//dao.delete("WD1321");
		//System.out.println(dao.exists("WD1321"));
		//System.out.println(dao.getSummaryResource("WD1321"));
	}*/
}