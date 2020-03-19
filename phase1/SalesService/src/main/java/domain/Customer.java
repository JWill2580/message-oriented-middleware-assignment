package domain;


public class Customer{

	private String id;
	private String email;
	private String group;

	public Customer() {
	}

	public Customer(String id, String email, String group) {
		this.id = id;
		this.email = email;
		this.group = group;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Customer{" + "id=" + id + ", email=" + email + ", group=" + group + '}';
	}

	
}
