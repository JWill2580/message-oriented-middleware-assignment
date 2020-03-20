/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Customer;

/**
 *
 * @author wiljo912
 */
public class Sale {
	String id;
	String saleDate;
	Customer customer;

	public Sale() {
	}

	public Sale(String id, String saleDate, Customer customer) {
		this.id = id;
		this.saleDate = saleDate;
		this.customer = customer;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Sale{" + "id=" + id + ", saleDate=" + saleDate + ", customer=" + customer + '}';
	}


	
}
