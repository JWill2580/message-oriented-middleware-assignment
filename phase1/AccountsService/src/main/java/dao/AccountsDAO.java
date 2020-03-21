package dao;

import domain.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccountsDAO {

	private static final Map<String, Account> accounts = new TreeMap<>();

	/*
	 * Some dummy data for testing
	 */
	static {
		if (accounts.isEmpty()) {
			accounts.put("WD1234", new Account("WD1234", "josiewalberg@hotmail.com", "Walbergers", "Josie", "Walberg", "low","http://localhost:8080/api/accounts/account/WD1234"));
			accounts.put("HG2142", new Account("HG2142", "candice@hotmail.com", "Canthis", "Candice", "This", "high","http://localhost:8080/api/accounts/account/HG2142"));

		}
	}

	/**
	 * Gets all account in the catalogue ordered by ID.
	 *
	 * @return All account ordered by ID.
	 */
	public List<Account> getAccounts() {
		return new ArrayList<>(accounts.values());
	}

	/**
	 * Adds a account to the catalogue.
	 *
	 * @param item The account being added.
	 */
	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	/**
	 * Gets a single account that matches the given ID.
	 *
	 * @param id The ID to search for.
	 * @return The product matching the given ID, or null if no match found.
	 */
	public Account getById(String id) {
		return accounts.get(id);
	}

	/**
	 * Deletes a account.
	 *
	 * @param id The ID of the account to delete.
	 */
	public void delete(String id) {
		accounts.remove(id);
	}

	/**
	 * Updates an account (effectively replaces it).
	 *
	 * @param id The ID of the account to replace.
	 * @param updatedAccount The account to replace it with.
	 */
	public void updateAccount(String id, Account updatedAccount) {
		accounts.put(id, updatedAccount);
	}

	/**
	 * Checks if an account exists.
	 *
	 * @param id The ID of the account being checked.
	 * @return <code>true</code> if product exists, <code>false</code> if not.
	 */
	public boolean exists(String id) {
		return accounts.containsKey(id);
	}

}
