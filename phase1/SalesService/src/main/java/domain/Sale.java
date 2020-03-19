/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author wiljo912
 */
public class Sale {
	String id;
	String saleDate;

	public Sale() {
	}

	public Sale(String id, String saleDate) {
		this.id = id;
		this.saleDate = saleDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	@Override
	public String toString() {
		return "Sale{" + "id=" + id + ", saleDate=" + saleDate + '}';
	}
	
	
}
