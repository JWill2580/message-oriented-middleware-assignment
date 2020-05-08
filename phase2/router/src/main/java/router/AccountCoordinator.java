/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package router;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import creator.CustomerCreator;
import creator.AccountCreator;
import domain.Account;
import domain.Customer;

/**
 *
 * @author admin
 */
public class AccountCoordinator extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:9000/api?enableCORS=true")
                // make message in-only so web browser doesn't have to wait on a non-existent response
                .setExchangePattern(ExchangePattern.InOnly)
                .log("${body}")
                .unmarshal().json(JsonLibrary.Gson, Account.class) 
                .to("jms:queue:first-queue");

        //Bean to create customer
        from("jms:queue:first-queue")
                .log("Before customer ${body}")
                .bean(CustomerCreator.class, 
                        "createCustomer("
                        + "${body.username},"
                        + "${body.firstName},"
                        + "${body.lastName},"
                        + "${body.email})")
                .log("After customer ${body}")
                .to("jms:queue:vend-rest");
        
        //Sending to vend via REST
        from("jms:queue:vend-rest")
                // remove headers so they don't get sent to Vend
                .removeHeaders("*")
                // add authentication token to authorization header
                .setHeader("Authorization", constant("Bearer KiQSsELLtocyS2WDN5w5s_jYaBpXa0h2ex1mep1a"))
                // marshal to JSON
                .marshal().json(JsonLibrary.Gson)  // only necessary if the message is an object, not JSON
                .setHeader(Exchange.CONTENT_TYPE).constant("application/json")
                // set HTTP method
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                // send it
                .to("https://info303otago.vendhq.com/api/2.0/customers")
                //.to("http://localhost:8089/api/2.0/customers")
                // store the response
                .to("jms:queue:vend-response");
        
        //extracting wrapper objects
        from("jms:queue:vend-response")
                .setBody().jsonpath("$.data")
                .marshal().json(JsonLibrary.Gson)                
                .to("jms:queue:extracted-response");
        
        //Unmarshal 
        from("jms:queue:extracted-response")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Gson, Customer.class) //potential issue here
                .to("jms:queue:account-creator");
        //comment out below this and it works
        
        //Another bean to create account
        /*from("jms:queue:account-creator")
                .log("Before account ${body}")
                .bean(AccountCreator.class, 
                        "createAccount("
                        + "${body.id}, "
                        + "${body.email},"
                        + "${body.username}, "
                        + "${body.firstName}, "
                        + "${body.lastName}, "
                        + "${body.group})")
                .log("After account ${body}")
                .to("jms:queue:account-rest");*/
        
        from("jms:queue:account-creator")
                .log("Before account ${body}")
                .bean(AccountCreator.class, 
                        "createAccount("
                        + "${body.id},"
                        + "${body.email},"
                        + "${body.customer_code},"
                        + "${body.first_name},"
                        + "${body.last_name},"                
                        + "${body.customer_group_id})")
                .log("After account ${body}")
                .to("jms:queue:account-rest");
        
        //send to service
        
    }
    
}
