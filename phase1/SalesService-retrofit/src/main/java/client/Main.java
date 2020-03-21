/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.CustomerApi;
import api.SalesApi;
import api.SaleApi;
import domain.Customer;
import domain.Sale;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author wiljo912
 */
public class Main {

	public static void main(String[] args) throws IOException {
		Retrofit retrofit = new Retrofit.Builder()
				  .baseUrl("http://localhost:8080/api/")
				  .addConverterFactory(GsonConverterFactory.create())
				  .build();
		
		// create an instance of the generated API
		SalesApi salesApi = retrofit.create(SalesApi.class);
		SaleApi saleApi = retrofit.create(SaleApi.class);
		CustomerApi customerApi = retrofit.create(CustomerApi.class);
		
		Customer customer1 = new Customer();
		customer1.setId("123");
		customer1.setEmail("Josh@gmail.com");
		customer1.setGroup("low");
		
		
		//Service needs to be create a new sale
		Sale sale1 = new Sale();
		sale1.setId("3");
		sale1.setSaleDate("2/2/2");
		sale1.setCustomer(customer1);
		
	
		
		//Create a new sale
		salesApi.createNewSale(sale1).execute().body();
		//System.out.println(salesApi.salesGet().execute().body());
		
		//Delete a sale
		//saleApi.deleteASale("1").execute().body();
		
		//customerApi.getSaleForSpecificCustomer(customer1.getId()).execute().body();
		//System.out.println(customer1.getId());
		//System.out.println(sale1.getCustomer());
		
		System.out.println(saleApi.getSaleForSpecificCustomer("1").execute().body());
		
		/*List<Sale> sales = customerApi.getSaleForSpecificCustomer("1").execute().body();
		for(Sale s: sales){
			System.out.println(s);
		}*/
	}
}