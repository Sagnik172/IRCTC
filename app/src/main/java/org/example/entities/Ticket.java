package org.example.entities;


import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
//    private String travelDate;
    private Train train;
    private List<Integer> seats=new ArrayList<>();
    public Ticket()
    {}

    public Ticket(String ticketId, String userId, String source, String destination, Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
//        this.travelDate = travelDate;
        this.train = train;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public String getTravelDate() {
//        return travelDate;
//    }

//    public void setTravelDate(String travelDate) {
//        this.travelDate = travelDate;
//    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
    public void addSeat(Integer seat)
    {

        seats.add(seat);

    }

    public void viewTicketInfo()
    {
        out.println("------------------------------------------------------------");
        out.println("Ticket id -->"+ticketId);
        out.println("User id -->"+userId);
        out.println("Source  -->"+source);
        out.println("Destination -->"+destination);
//        out.println("Travel String -->"+travelDate);
        out.println("Train id -->"+this.train.getTrainId());
        out.println("Train No -->"+this.train.getTrainNo());
        out.print("Seats booked ->");
        for(Integer seat:seats)
        {
            out.println(seat+" ");
        }
        out.println("------------------------------------------------------------");
    }

}
