/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author wiljo912
 */
public class Sale {
	private String id;
	private String saleDate;
	private Customer customer;
	private Totals total;
	private Collection<SaleItem> saleItems = new ArrayList<SaleItem>();

	public Sale() {
	}

	public Sale(String id, String saleDate, Customer customer, Totals total, SaleItem saleItem) {
		this.id = id;
		this.saleDate = saleDate;
		this.customer = customer;
		this.total = total;
		this.saleItems.add(saleItem);
	}
	
	public Sale(String id, String saleDate, Customer customer, Totals total) {
		this.id = id;
		this.saleDate = saleDate;
		this.customer = customer;
		this.total = total;
	}

	public Totals getTotal() {
		return total;
	}

	public void setTotal(Totals total) {
		this.total = total;
	}

	public Collection<SaleItem> getSaleItem() {
		return saleItems;
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

	public void addSaleItem(SaleItem item){
		this.saleItems.add(item);
	}
	
	@Override
	public String toString() {
		return "Sale{" + "id=" + id + ", saleDate=" + saleDate + ", customer=" + customer + '}';
	}
}