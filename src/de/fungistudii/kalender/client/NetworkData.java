/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client;

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

    public static class StornoRequest {

        public int id;
    }

    public static class RemoveBlockRequest {

        public int id;
    }

    public static class BlockRequest {

        public Date start;
        public int duration;
        public int friseur;
        public String msg;
    }

    public static class EditVacationRequest {

        public int workerId;
        public Date start;
        public Date end;
        public int id;
    }

    public static class CreateVacationRequest {
        public int workerId;
    }

    public static class RemoveVacationRequest {

        public int workerId;
        public int id;
    }

    public static class CreateServiceRequest {

        public String name;
        public int category;
        public int duration;
        public int einwirkZeit;
        public int price;
    }

    public static class RemoveServiceRequest {
        int id;
    }
}
