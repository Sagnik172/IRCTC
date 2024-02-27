package org.example.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private List<String> stations;
    private Map<String, String> stationTime;
    public Train()
    {

    }


    public Train(String trainId, String trainNo, List<List<Integer>> seats, List<String> stations, Map<String, String> stationTime) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stations = stations;
        this.stationTime = stationTime;
    }
    public void fillSeat(Integer seatId)
    {
        int row = seatId / 8;
        int col = seatId % 8;

        List<Integer> rowList = seats.get(row); // Retrieve the inner list
        rowList.set(col, 1); // Set the value at the specified column

        // Update the seats 2D array accordingly
        seats.set(row, rowList);
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public Map<String, String> getStationTime() {
        return stationTime;
    }

    public void setStationTime(Map<String, String> stationTime) {
        this.stationTime = stationTime;
    }

    public Integer emptySeatPresent()
    {
        Integer cnt=0;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(seats.get(i).get(j).equals(0))
                {
                   cnt++;
                }
            }
        }
        return cnt;
    }
    public List<Integer> getEmptySeats()
    {
        List<Integer> emptySeats=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(seats.get(i).get(j).equals(0))
                {
                    Integer num=i*8+j+1;
                    emptySeats.add(num);

                }
            }
        }
        return emptySeats;
    }



    public Boolean validTrain(String source,String destination,Integer seatsRequired)
    {
        int s1=stations.indexOf(source);
        int d1=stations.indexOf(destination);
        return s1!=-1 && d1!=-1 && s1<d1 && emptySeatPresent()>=seatsRequired;
    }



    public void TrainInfo()
    {
        System.out.println("------------------------------------------------------------");
        System.out.println("Train id -->"+trainId);
        System.out.println("Train no -->"+trainNo);
//        System.out.println("Source  -->"+source);
//        System.out.println("Destination -->"+destination);
//        System.out.println("Travel String -->"+travelDate);
//        System.out.println("Train id -->"+this.train.getTrainId());
//        System.out.println("Train No -->"+this.train.getTrainNo());
        System.out.println("------------------------------------------------------------");
    }

}
