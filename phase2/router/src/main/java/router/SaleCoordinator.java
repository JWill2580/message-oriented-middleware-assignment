/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package router;

import creator.AccountCreator;
import creator.CustomerCreator;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.camel.builder.RouteBuilder;
import creator.SummaryCreator;
import domain.Customer;
import domain.Sale;
import domain.SaleItem;
import domain.Summary;
import org.apache.camel.Exchange;
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
                .to("jms:queue:extract-properties");     
      
        
        from("jms:queue:extract-properties")
                .unmarshal().json(JsonLibrary.Gson, Sale.class) 
                .setProperty("group").simple("${body.customer.group}")
                .setProperty("username").simple("${body.customer.customerCode}")//minor change here
                .setProperty("id").simple("${body.customer.id}")
                .setProperty("firstname").simple("${body.customer.firstName}")
                .setProperty("lastname").simple("${body.customer.lastName}")
                .setProperty("email").simple("${body.customer.email}")
                //.log("${properties}")
                .to("jms:queue:sales-service");
        
        from("jms:queue:sales-service")
                .marshal().json(JsonLibrary.Gson) // only necessary if object needs to be converted to JSON
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/api/sales")
                .to("jms:queue:post-response");
 
        from("jms:queue:post-response")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .toD("http://localhost:8081/api/sales/customer/${exchangeProperty.id}/summary")
                .log("summary is ${body}")
                .to("jms:queue:get-response");
        
        //bean to create summary using group data to get group id
        from("jms:queue:get-response")
                .unmarshal().json(JsonLibrary.Gson, Summary.class) 
                .log("Before Summary ${body}")
                .bean(SummaryCreator.class, 
                        "createSummary("
                        + "${body.numberOfSales},"
                        + "${body.totalPayment},"
                        + "${body.group})")
                .log("After Summary ${body}")
                .to("jms:queue:compare-group");
        
        from("jms:queue:compare-group")
                .choice()
                //.when().simple("${body.group} == '0afa8de1-147c-11e8-edec-2b197906d816'")
                .when().simple("${body.group} == ${exchangeProperty.group}")
                .to("jms:queue:update")
                .otherwise()
                .to("jms:queue:update");
        
        from("jms:queue:update")
                //bean
                .log("Before Customer ${body}")
                .bean(CustomerCreator.class, 
                        "updateCustomer("
                        + "${exchangeProperty.id},"
                        + "${exchangeProperty.username},"
                        + "${exchangeProperty.firstname},"
                        + "${exchangeProperty.lastname},"
                        + "${exchangeProperty.email},"
                        + "${exchangeProperty.group})")
                .log("After Customer ${body}")
                .multicast()
                .to("jms:queue:bean-account", "jms:queue:vend-rest");
        
        //Works till here
        from("jms:queue:bean-account")
                .log("Before Account ${body}")
                .bean(AccountCreator.class, 
                        "createAccount("
                        + "${body.id}, "
                        + "${body.group},"
                        + "${body.email}, "
                        + "${body.firstName}, "
                        + "${body.lastName}, "
                        + "${body.customerCode})")
                .log("After account ${body}")
                .to("jms:queue:rest-account");
        
        //send to customer accounts service
        from("jms:queue:rest-account")
                .marshal().json(JsonLibrary.Gson) // only necessary if object needs to be converted to JSON
                .log("${body}")
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8086/api/accounts/account/${exchangeProperty.id}")
                .to("jms:queue:http-response-accounts");  // HTTP response ends up in this queue               
                
               
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

