/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
		
		List<Account> prods = accountsApi.accountsGet().execute().body();
		System.out.println(prods);
	}
}
