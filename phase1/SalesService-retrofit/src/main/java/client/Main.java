/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.CustomerApi;
import api.SalesApi;
import api.SaleApi;
import domain.Sale;
import java.io.IOException;
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
		
		//Service needs to be create a new sale
		Sale sale1 = new Sale();
		sale1.setId("3");
		sale1.setSaleDate("2/2/2");
		
		//Add a new sale
		salesApi.createNewSale(sale1).execute().body();
		
		//Delete a sale
		saleApi.deleteASale("1").execute().body();
	}
}