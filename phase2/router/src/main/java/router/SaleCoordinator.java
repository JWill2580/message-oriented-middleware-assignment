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
      
        //Extracting properities to be used for later use in beans
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
        
        //sending sale to our service
        from("jms:queue:sales-service")
                .marshal().json(JsonLibrary.Gson) // only necessary if object needs to be converted to JSON
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/api/sales")
                .to("jms:queue:post-response");
 
        //Getting 'calculated resource' summary data from service
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
        
        //Comparing groups to see whether should change
        from("jms:queue:compare-group")
                .choice()
                //.when().simple("${body.group} == '0afa8de1-147c-11e8-edec-2b197906d816'")
                .when().simple("${body.group} == ${exchangeProperty.group}")
                .to("jms:queue:done")
                .otherwise()
                .to("jms:queue:update");
        
        //Change required so calling update customer method
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
                //Split into creating the new account and updating service
                //and PUTting new customer into vend service
                .to("jms:queue:bean-account", "jms:queue:vend-rest-2");
        
        //Creating account with bean
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
                .removeHeaders("*") // remove headers to stop them being sent to the service
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .log("before")
                .toD("http://localhost:8086/api/accounts/account/${exchangeProperty.id}")
                .log("after")
                .to("jms:queue:http-response-accounts");  // HTTP response ends up in this queue               
        
        //send to vend service
        from("jms:queue:vend-rest-2")
                .log("hello ${body}")  
                 // remove headers so they don't get sent to Vend
                .removeHeaders("*")
                // add authentication token to authorization header
                .setHeader("Authorization", constant("Bearer KiQSsELLtocyS2WDN5w5s_jYaBpXa0h2ex1mep1a"))
                // marshal to JSON
                .marshal().json(JsonLibrary.Gson)  // only necessary if the message is an object, not JSON
                .setHeader(Exchange.CONTENT_TYPE).constant("application/json")
                // set HTTP method
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                // send it                
                .toD("https://info303otago.vendhq.com/api/2.0/customers/${exchangeProperty.id}")
                // store the response
                .to("jms:queue:vend-response-2");
               
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

