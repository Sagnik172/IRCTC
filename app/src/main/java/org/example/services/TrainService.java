package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TrainService {
    private List<Train> trainList;
    private final ObjectMapper objectMapper= new ObjectMapper();
    private static final String TRAINPATH="app/src/main/java/org/example/localDb/Trains.json";
    private Map<Integer,List<Integer>> mp=new HashMap<>();
    public  Boolean isValidSeat(Integer seatId,Integer seatPrefrence)
    {
        for(Integer num:mp.get(seatPrefrence))
        {
            if(seatId%8==num)
                return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }
    public Boolean updateTrainSeat(Integer seat,Train train)
    {
        train.fillSeat(seat-1);
        trainList=trainList.stream().filter(train1 -> !train1.getTrainId().equals(train.getTrainId())).collect(Collectors.toList());
        trainList.add(train);
        try
        {
            saveTraintoFIle();
            return Boolean.TRUE;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return Boolean.FALSE;
        }

    }


    public TrainService() throws IOException
    {
        File fl=new File(TRAINPATH);
        trainList=new ArrayList<>();
        mp.put(1,Arrays.asList(3,4));
        mp.put(2,Arrays.asList(2,5));
        mp.put(3,Arrays.asList(1,6));
        mp.put(4, Collections.singletonList(7));
        mp.put(5,Collections.singletonList(0));


        try {
            if (fl.exists() && fl.length() > 0) {
                trainList = objectMapper.readValue(fl, new TypeReference<List<Train>>() {});
            } else {
                // Handle case when file does not exist or is empty
                System.out.println("User file does not exist or is empty.");
            }
        } catch (IOException e) {
            // Handle IOException (e.g., file not found, invalid JSON format)
//            System.out.println("sth wr");
            e.printStackTrace();
        }


    }

    public void addTrain()
    {
         String trainId= UUID.randomUUID().toString();
         String trainNo=UUID.randomUUID().toString();
         List<List<Integer>> seats = new ArrayList<>();


        for (int i = 0; i < 8; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                row.add(0);
            }
            seats.add(row);
        }
        List<String> stations=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        System.out.println("How many stations");
        int num=sc.nextInt();
        sc.nextLine();

        for(int i=0;i<num;i++)
        {
            System.out.println("Enter station"+(i+1));
            String str=sc.next();
            stations.add(str);

        }


         Map<String, String> stationTime=new HashMap<>();
         for(int i=0;i<stations.size();i++)
         {
             stationTime.put(stations.get(i),Integer.toString(i));
         }
         Train train=new Train(trainId,trainNo,seats,stations,stationTime);
         trainList.add(train);
         try
         {
             saveTraintoFIle();
         }
         catch (IOException ex)
         {
             ex.printStackTrace();

         }


    }
    public void saveTraintoFIle() throws IOException
    {
        File fl=new File(TRAINPATH);
        objectMapper.writeValue(fl,trainList);
    }
    public Optional<List<Train>> searchTrain(String source,String destination,Integer seatsRequired)
    {
        Optional<List<Train>> temp=Optional.ofNullable(trainList.stream().filter(train -> train.validTrain(source,destination,seatsRequired)).collect(Collectors.toList()));
        return temp;

    }








}
