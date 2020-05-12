/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creator;

import domain.Account;

/**
 *
 * @author admin
 */
public class AccountCreator {
    public Account createAccount(String id,  String group, String email, String firstName, String lastName, String username){
        Account acc = new Account();
        acc.setId(id);
        acc.setGroup(group);
        acc.setEmail(email);
        acc.setFirstName(firstName);
        acc.setLastName(lastName);
        acc.setUsername(username);
        //acc.setUri("");
        return acc;
    }
}
