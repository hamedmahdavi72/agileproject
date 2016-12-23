package models;

/**
 * Created by HamedMahdavi on 12/21/2016.
 */
public class MongoLocation  {
    private final String type = "Point";
    private double[] coordinates = new double[2];

    public MongoLocation(){}

    public MongoLocation(double latitude, double longitude){

        coordinates[0] = latitude;
        coordinates[1] = longitude;
    }
    public String getType() {
        return type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double getLatitude(){
        return coordinates[0];
    }

    public double getLongitude(){
        return coordinates[1];
    }


}
