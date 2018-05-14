package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = Monument.TABLE_NAME,
        indices = {@Index(value = {Monument.COLUMN_NAME}, unique = true)})
public class Monument implements Serializable {

    /**
     * The name of the Monument table.
     */
    public static final String TABLE_NAME = "monuments";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the name column.
     */
    public static final String COLUMN_NAME = "name";
    /**
     * The name of the latitude column.
     */
    public static final String COLUMN_LATITUDE = "latitude";
    /**
     * The name of the longitude column.
     */
    public static final String COLUMN_LONGITUDE = "longitude";
    /**
     * The name of the sin latitude column.
     */
    public static final String COLUMN_SIN_LATITUDE = "sin_latitude";
    /**
     * The name of the sin longitude column.
     */
    public static final String COLUMN_SIN_LONGITUDE = "sin_longitude";
    /**
     * The name of the cos latitude column.
     */
    public static final String COLUMN_COS_LATITUDE = "cos_latitude";
    /**
     * The name of the cos longitude column.
     */
    public static final String COLUMN_COS_LONGITUDE = "cos_longitude";
    /**
     * The name of the cos distance column.
     */
    public static final String COLUMN_COS_DISTANCE = "cos_distance";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the monument.
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true, name = COLUMN_ID)
    public String id;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_LATITUDE)
    private double latitude;

    @ColumnInfo(name = COLUMN_LONGITUDE)
    private double longitude;

    @ColumnInfo(name = COLUMN_SIN_LATITUDE)
    private double sinLatitude;

    @ColumnInfo(name = COLUMN_SIN_LONGITUDE)
    private double sinLongitude;

    @ColumnInfo(name = COLUMN_COS_LATITUDE)
    private double cosLatitude;

    @ColumnInfo(name = COLUMN_COS_LONGITUDE)
    private double cosLongitude;

    @ColumnInfo(name = COLUMN_COS_DISTANCE)
    private double cosDistance;

    public Monument() {
    }

    public Monument(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Monument(String name, double latitude, double longitude,
                    double sinLatitude, double sinLongitude,
                    double cosLatitude, double cosLongitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sinLatitude = sinLatitude;
        this.sinLongitude = sinLongitude;
        this.cosLatitude = cosLatitude;
        this.cosLongitude = cosLongitude;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public double getCosDistance() {
        return cosDistance;
    }

    public void setCosDistance(double cosDistance) {
        this.cosDistance = cosDistance;
    }
}
