package master2019.flink.YellowTaxiTrip;


import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.tuple.Tuple18;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.sql.Timestamp;
import org.apache.flink.api.common.functions.MapFunction;

public class Main {

    public static final String OUT_JFK_ALARMS = "jfkAlarms.csv";
    public static final String OUT_LARGE_TRIPS = "largeTrips.csv";

    public static void main(String[] args){

        final ParameterTool params = ParameterTool.fromArgs(args);
        // set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // get input data
        DataStream<String> text;
        // read the text file from given input path
        text = env.readTextFile(params.get("input"));
        //String p = "/Users/juanluisrto/Documents/Universidad/UPM/'Cloud Computing'/YellowTaxiTrip/yellow_tripdata_2019_06.csv";
        //text = env.readTextFile(p);

        SingleOutputStreamOperator<Tuple18<Integer, Timestamp,Timestamp,Integer,Float,Integer,String,Integer,Integer,Integer,Float,Float,Float,Float,Float,Float,Float,Float>> mapStream = text.
                map(new MapFunction<String, Tuple18<Integer, Timestamp,Timestamp,Integer,Float,Integer,String,Integer,Integer,Integer,Float,Float,Float,Float,Float,Float,Float,Float>>(){
                    public Tuple18 <Integer, Timestamp,Timestamp,Integer,Float,Integer,String,Integer,Integer,Integer,Float,Float,Float,Float,Float,Float,Float,Float>
                        map(String in){
                        String[] fieldArray = in.split(",");
                        Tuple18<Integer, Timestamp,Timestamp,Integer,Float,Integer,String,Integer,Integer,Integer,Float,Float,Float,Float,Float,Float,Float,Float>
                            out = new Tuple18(
                                Integer.parseInt(fieldArray[0]),    //VendorID
                                Timestamp.valueOf(fieldArray[1]),   //tpep_pickup_datetime
                                Timestamp.valueOf(fieldArray[2]),   //tpep_dropoff_datetime
                                Integer.parseInt(fieldArray[3]),    //passenger_count
                                Float.parseFloat(fieldArray[4]),    //trip_distance
                                Integer.parseInt(fieldArray[3]),    //RatecodeID,
                                fieldArray[6],                      //store_and_fwd_flag
                                Integer.parseInt(fieldArray[7]),    //PULocationID
                                Integer.parseInt(fieldArray[8]),    //DOLocationID
                                Integer.parseInt(fieldArray[9]),    //payment_type
                                Float.parseFloat(fieldArray[10]),    //fare_amount
                                Float.parseFloat(fieldArray[11]),    //extra
                                Float.parseFloat(fieldArray[12]),    //mta_tax
                                Float.parseFloat(fieldArray[13]),    //tip_amount
                                Float.parseFloat(fieldArray[14]),    //tolls_amount
                                Float.parseFloat(fieldArray[15]),    //improvement_surcharge
                                Float.parseFloat(fieldArray[16]),    //total_amount
                                Float.parseFloat(fieldArray[17])    //congestion_surcharge.
                                );
                        return out;
                    }
                });


    }
}