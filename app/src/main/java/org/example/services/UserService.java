package org.example.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.User;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserService {
    private List<User> userList=new ArrayList<>();
    private int flag=0;
    private User user;
    private final ObjectMapper objectMapper= new ObjectMapper();
    private static final String USERPATH="app/src/main/java/org/example/localDb/Users.json";


    public UserService(User user) throws java.io.IOException
    {
        this.user=user;

        File fl=new File(USERPATH);
        try {
            if (fl.exists() && fl.length() > 0) {
                userList = objectMapper.readValue(fl, new TypeReference<List<User>>() {});
            } else {
                // Handle case when file does not exist or is empty
                System.out.println("User file does not exist or is empty.");
            }
        } catch (IOException e) {
            // Handle IOException (e.g., file not found, invalid JSON format)
            e.printStackTrace();
        }




    }
    public Optional<User> loginUser()
    {
        Optional<User> op=userList.stream().filter(user->{
           return user.getName().equals(this.user.getName()) && user.getPassword().equals(this.user.getPassword());
        }).findFirst();
        return op;
    }
    public Boolean Signup()
    {
        try
        {
            userList.add(user);
            flag=1;
            savetoFileUser();
            return Boolean.TRUE;
        }
        catch(IOException ex)
        {
            return Boolean.FALSE;
        }

    }
    private void savetoFileUser() throws IOException
    {
        File usersFile=new File(USERPATH);
        objectMapper.writeValue(usersFile,userList);
    }

    public void fetchBooking()
    {
        user.printTickets();
    }
    public void showTicketBookings()
    {
        user.printTickets();
    }

    public Boolean addBooking(Ticket ticket)
    {
        List<Ticket> ticketBooked=user.getTicketsBooked();
        ticketBooked.add(ticket);
        user.setTicketsBooked(ticketBooked);
        try
        {
            userList=userList.stream().filter(user-> !(this.user.getUserId().equals(user.getUserId()))).collect(Collectors.toList());
            userList.add(user);
            savetoFileUser();
            return Boolean.TRUE;

        }
        catch (IOException ex)
        {
            System.out.println("Could not add Booking");
            return Boolean.FALSE;
        }
    }

    public Boolean cancelBooking(String ticketId)
    {
        try
        {
            List<Ticket> temp=user.getTicketsBooked();
            temp=temp.stream().filter(ticket -> !(ticket.getTicketId().equals(ticketId))).collect(Collectors.toList());
           user.setTicketsBooked(temp);
            userList=userList.stream().filter(user-> !(this.user.getUserId().equals(user.getUserId()))).collect(Collectors.toList());
            userList.add(user);
            savetoFileUser();
            return Boolean.TRUE;

        }
        catch (IOException ex)
        {
            System.out.println("Could not Cancel booking");
            return Boolean.FALSE;
        }

    }


}
