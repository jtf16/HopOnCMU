package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;

public class Monument implements Serializable {

    private static final long serialVersionUID = -8807331723807741905L;

    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double sinLatitude;
    private double sinLongitude;
    private double cosLatitude;
    private double cosLongitude;

    public Monument() {
    }

    public Monument(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Monument(String id, String name, double latitude, double longitude,
                    double sinLatitude, double sinLongitude,
                    double cosLatitude, double cosLongitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sinLatitude = sinLatitude;
        this.sinLongitude = sinLongitude;
        this.cosLatitude = cosLatitude;
        this.cosLongitude = cosLongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSinLatitude() {
        return sinLatitude;
    }

    public void setSinLatitude(double sinLatitude) {
        this.sinLatitude = sinLatitude;
    }

    public double getSinLongitude() {
        return sinLongitude;
    }

    public void setSinLongitude(double sinLongitude) {
        this.sinLongitude = sinLongitude;
    }

    public double getCosLatitude() {
        return cosLatitude;
    }

    public void setCosLatitude(double cosLatitude) {
        this.cosLatitude = cosLatitude;
    }

    public double getCosLongitude() {
        return cosLongitude;
    }

    public void setCosLongitude(double cosLongitude) {
        this.cosLongitude = cosLongitude;
    }
}
