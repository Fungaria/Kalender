package de.fungistudii.kalender.client.database;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sreis
 */
@XmlRootElement(name = "account")
public class Account {
    public String username;
    public String password;
}
