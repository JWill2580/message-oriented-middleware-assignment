/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package router;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author admin
 */
public class AccountCoordinator extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:9000/blargh?enableCORS=true")
                // make message in-only so web browser doesn't have to wait on a non-existent response
                .setExchangePattern(ExchangePattern.InOnly)
                .log("${body}")
                .to("jms:queue:queue-that-processes-message");
        

    }
    
}
