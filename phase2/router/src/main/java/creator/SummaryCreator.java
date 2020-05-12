/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creator;

import domain.Summary;

/**
 *
 * @author admin
 */
public class SummaryCreator {
        public Summary createSummary(Integer sales, double payment, String group){
            Summary sum = new Summary();
            sum.setNumberOfSales(sales);
            sum.setTotalPayment(payment);
            if(group.equals("Regular Customers")){
                sum.setGroup("0afa8de1-147c-11e8-edec-2b197906d816");
            } else {
                sum.setGroup("0afa8de1-147c-11e8-edec-201e0f00872c");
            }
            return sum;
        }
}
