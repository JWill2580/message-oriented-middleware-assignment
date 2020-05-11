/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package router;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.camel.builder.RouteBuilder;
import creator.SaleCreator;
import domain.Sale;
import domain.SaleItem;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 *
 * @author admin
 */
public class SaleCoordinator extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        //IMAP web hook
        from("imaps://outlook.office365.com?username=wiljo912@student.otago.ac.nz"
                + "&password=" + getPassword("Enter your E-Mail password")
                + "&searchTerm.subject=Vend:SaleUpdate")
                .convertBodyTo(String.class)
                .log("Found ${body}")
                .multicast()
                .to("jms:queue:extract-properties", "jms:queue:extract-sale");     
      
        
        from("jms:queue:extract-properties")
                .unmarshal().json(JsonLibrary.Gson, Sale.class) 
                .setProperty("customerid").simple("${body.customer.customer_group_id}")
                .setProperty("id").simple("${body.customer.id}")
                .setProperty("firstname").simple("${body.customer.first_name}")
                .setProperty("lastname").simple("${body.customer.last_name}")
                .setProperty("email").simple("${body.customer.email}")
                .log("$properties")
                .to("jms:queue:replace");
 /*
        //bean to extract sale
        from("jms:queue:extract-sale")
                .log("Before sale ${body}")
                .bean(SaleCreator.class, 
                        "createSale("
                        + "${body.username},"
                        + "${body.firstName},"
                        + "${body.lastName},"
                        + "${body.email})")
                .log("After sale ${body}")
                .to("jms:queue:sale-rest");
        */
        
    }
    
    public static String getPassword(String prompt) {
        JPasswordField txtPasswd = new JPasswordField();
        int resp = JOptionPane.showConfirmDialog(null, txtPasswd, prompt,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resp == JOptionPane.OK_OPTION) {
            String password = new String(txtPasswd.getPassword());
            return password;
        }
        return null;
    }
    
}

