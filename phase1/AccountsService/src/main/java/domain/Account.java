package domain;

import java.math.BigDecimal;

public class Account implements Comparable<Account>{

	private String id;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private String group;
	private String uri;

	public Account() {
	}

	public Account(String id, String email, String username, String firstName, String lastName, String group, String uri) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
		this.uri = uri;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "Account{" + "id=" + id + ", email=" + email + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", group=" + group + ", uri=" + uri + '}';
	}

	@Override
	public int compareTo(Account other) {
		return this.id.compareTo(other.getId());
	}
}
