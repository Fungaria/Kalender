/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database;

import java.util.Date;

/**
 *
 * @author sreis
 */
public class NetworkData {

    public static class LoginRequest {

        public String username;
        //encryption is for n00bs i think
        public String password;
    }

    public static class LoginResponse {

        public boolean sucess;
        public String msg;
    }

    public static class TerminRequest {
        public int kundenId;
        public Date start;
        public int duration;
        public int serviceId;
        public int friseurId;
        public int urheber;
    }
}
