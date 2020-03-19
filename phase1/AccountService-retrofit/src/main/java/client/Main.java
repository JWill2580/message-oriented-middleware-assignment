/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import api.AccountApi;
import api.AccountsApi;
import domain.Account;
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
		AccountsApi accountsApi = retrofit.create(AccountsApi.class);
		AccountApi accountApi = retrofit.create(AccountApi.class);
		
		Account account1 = new Account();
		account1.setId("fd");
		account1.setEmail("Yo@gmail.com");
		account1.setUsername("JWill");
		account1.setFirstname("Josh");
		account1.setLastname("Wilson");
		account1.setGroup("low");
		account1.setUri("http://localhost:8080/api/catalogue/product/fd");
		
		accountsApi.createNewAccount(account1).execute().body();
		
		accountApi.updateDetailsOfExistingAccount(account1, "gfds");
		
		List<Account> prods = accountsApi.accountsGet().execute().body();
		System.out.println(prods);
	}
}
