/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.services.TrainService;
import org.example.services.UserService;
import org.example.services.UserServiceUtil;

import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

public class App {


    public static void main(String[] args) {
       System.out.println("IRCTC Server started...");
       Scanner sc=new Scanner(System.in);
       int choice;


       System.out.println("1.Signup");
       System.out.println("2.Login");
       choice=sc.nextInt();
       String userId;
       String password;
       String name;

       userId= UUID.randomUUID().toString();
       System.out.println("Enter name");
       name=sc.next();
       System.out.println("Enter password");
       password=sc.next();
       List<Ticket> temp=new ArrayList<>();
       User user=new User(userId,name,password,temp, UserServiceUtil.getPassword(password));
       UserService us;
        try{
            us=new UserService(user);

        }catch(IOException ex){
            System.out.println("Something went wrong 1"+ex);
            return;

        }

       Optional<User> op=us.loginUser();
       if(choice==1)
       {

           if(!op.isPresent())
           {
               us.Signup();
           }
       }
       else if(choice==2)
       {
           if(!op.isPresent())
           {
               System.out.println("User not found");
               return;
           }
           user=op.get();
           try{
               us=new UserService(user);

           }catch(IOException ex){
               System.out.println("Something went wrong");
               return;

           }




       }
        TrainService trainService;
        try
        {
            trainService=new TrainService();

        }
        catch(IOException ex)
        {
            System.out.println("Something went wrong 4");
            return;
        }
       System.out.println(user.getUserId());
       System.out.println(user.getName());

       int choice2=0;
       while(choice2!=5)
       {
           System.out.println("1.Fetch Bookings");
           System.out.println("2.Book seat");
           System.out.println("3.Cancel booking");
           System.out.println("4.Add trains");

           System.out.println("5.Exit application");
           choice2=sc.nextInt();
           if(choice2>=5)
               break;
           switch (choice2)
           {
               case 1:us.fetchBooking();
               break;
               case 2:
                   String source,destination;
                   System.out.println("Enter source");
                   source=sc.next();
                   System.out.println("Enter destination");
                   destination=sc.next();
                   System.out.println("How many seats you want to book");
                   Integer seatsBooked=sc.nextInt();
                   sc.nextLine();
                   Optional<List<Train>> trainList= trainService.searchTrain(source,destination,seatsBooked);
                   User finalUser = user;
                   UserService finalUs = us;
                   if(trainList.isPresent())
                   {
                       List<String> trainIdList=new ArrayList<>();
                       for (int i=0;i<trainList.get().size();i++) {
                           Train train=trainList.get().get(i);
                           train.TrainInfo();
                           trainIdList.add(train.getTrainId());
                       }
                       String str;
                       System.out.println("Enter the train id u want to book");
                       str=sc.next();
                       while(!trainIdList.contains(str))
                       {
                           System.out.println("Wrong train id...Please Enter again");
                           str=sc.next();
                       }
                       final String searchId=str;

                       Train requiredTrain = null;
                       for (int i=0;i<trainList.get().size();i++) {
                           if(trainList.get().get(i).getTrainId().equals(searchId))
                           {
                               requiredTrain=trainList.get().get(i);
                               break;
                           }

                       }
                       List<Integer> emptySeats=requiredTrain.getEmptySeats();
                       Integer bookedNow=0;
                       Ticket ticket=new Ticket(UUID.randomUUID().toString(), finalUser.getUserId(),source,destination,requiredTrain);
                       while (bookedNow!=seatsBooked)
                       {
                           System.out.println("For Passenger "+(bookedNow+1)+", select seat preference");
                           System.out.println("1.Upper");
                           System.out.println("2.Middle");
                           System.out.println("3.Lower");
                           System.out.println("4.Side Upper");
                           System.out.println("5.Side Lower");

                           Integer seatPreference=6;
                           Boolean found=Boolean.FALSE;
                           while(!(seatPreference>0 && seatPreference<6) || found==Boolean.FALSE)
                           {
                               System.out.println("Enter seat preference");
                               seatPreference=sc.nextInt();

                               for(Integer seat:emptySeats)
                               {
                                   if(trainService.isValidSeat(seat,seatPreference))
                                   {
                                       found=Boolean.TRUE;
                                       System.out.println("Seat booked-->"+seat);
                                       ticket.addSeat(seat);
                                       trainService.updateTrainSeat(seat,requiredTrain);


                                       bookedNow++;
                                       break;

                                   }
                               }
                               if(found==Boolean.TRUE)
                               {
                                   break;
                               }
                               else
                               {
                                   System.out.println("Please try again");
                               }

                           }

                       }
                       finalUs.addBooking(ticket);


                   }

                   if(!trainList.isPresent())
                   {
                       System.out.println("No trains available");
                   }

                   break;
               case 3:
                   us.showTicketBookings();
                   String str;
                   System.out.println("Enter ticket id:");
                   str=sc.next();
                   us.cancelBooking(str);
                   System.out.println("Now tickets are:");
                   us.showTicketBookings();
                   break;
               case 4:

                   trainService.addTrain();

                   break;



           }


       }





    }
}