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
import domain.SaleItem;
import domain.Total;
import java.io.IOException;
import java.util.ArrayList;
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
				
		//Creating test data
		Customer customer1 = new Customer();
		customer1.setId("123");
		customer1.setEmail("Josh@gmail.com");
		customer1.setGroup("low");
		
		Total total = new Total();
		total.setTotalPrice(4.0f);
		total.setTotalTax(3.0f);
		
		SaleItem item = new SaleItem();
		item.setPrice(2.0f);
		item.setProductId("M123");
		item.setQuantity(1.0f);
		
		Sale sale1 = new Sale();
		sale1.setId("3");
		sale1.setSaleDate("2/2/2");
		sale1.setCustomer(customer1);
		sale1.setTotal(total);
		//sale1.setSaleItem(item);
		
	
		//Getting all sales TODO: remove this functionality as is for testing
		salesApi.createNewSale(sale1).execute().body();
		//System.out.println(salesApi.salesGet().execute().body());
		
		//Delete a sale 2 that exists in the database
		//saleApi.deleteASale("2").execute().body();
		
		//Get sales with a specific id
		System.out.println(customerApi.salesCustomerIdGet("WD1321").execute().body());
	}
}