package org.example.entities;

import org.example.services.UserServiceUtil;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String password;
    private List<Ticket> ticketsBooked;
    private String hashedPassword;
    public User()
    {

    }

    public User(String userId, String name, String password,List<Ticket> ticketsBooked,String hashedPassword) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.ticketsBooked=ticketsBooked;
        this.hashedPassword=hashedPassword;
    }
    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
//        this.hashedPassword= UserServiceUtil.getPassword(password);
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

//    public String getHashedPassword() {
//        return hashedPassword;
//    }
    public void printTickets()
    {
        System.out.println("Your booked tickets are :");
        for (Ticket ticket : ticketsBooked) {
            ticket.viewTicketInfo();
        }
    }

}
