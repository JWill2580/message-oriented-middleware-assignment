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
public class Totals {
	Double totalPrice;
	Double totalTax;
	Double totalPayment;

	public Totals() {
	}

	public Totals(Double totalPrice, Double totalTax, Double totalPayment) {
		this.totalPrice = totalPrice;
		this.totalTax = totalTax;
		this.totalPayment = totalPayment;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	@Override
	public String toString() {
		return "Totals{" + "totalPrice=" + totalPrice + ", totalTax=" + totalTax + ", totalPayment=" + totalPayment + '}';
	}
}
