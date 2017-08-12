package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


/**
 * Created by David S. Martinez on 7/26/2017.
 */

    @DatabaseTable(tableName = "ENGAGEMENT")
    public class Engagement {

    @DatabaseField(columnName = "ENGAGEMENT_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "CREATED", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
         format = "yyyy-MM-dd HH:mm:ss", readOnly = true)
    private java.sql.Timestamp created;

    @DatabaseField(columnName = "ENGAGEMENT_NAME")
    private String nameOfEngagement;

    @DatabaseField(columnName = "CALIBER")
    private String caliber;

    @DatabaseField(columnName = "BARREL_LENGTH")
    private int length;

    @DatabaseField(columnName = "GRAINS")
    private int grains;

    @DatabaseField(columnName = "VELOCITY")
    private int velocity;

    @DatabaseField(columnName = "DISTANCE")
    private int distance;

    @DatabaseField(columnName = "WIND_DIR")
    private int windDir;

    @DatabaseField(columnName = "WIND_SPEED")
    private int windSpeed;

    @DatabaseField(columnName = "CLICK_VALUE_WINDAGE")
    private double clickValueWindage;

    @DatabaseField(columnName = "CLICK_VALUE_ELEVATION")
    private double clickValueElevation;

    @DatabaseField(columnName = "WINDAGE_ADJ")
    private int windageAdj;

    @DatabaseField(columnName = "ELEVATION_ADJ")
    private int elevationAdj;

    @DatabaseField(columnName = "TARGET_STYLE_ID", canBeNull = false, foreign = true)
    private TargetStyle targetStyle;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Shot> shots;

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s: %s", format.format(getCreated()), getNameOfEngagement());
    }

    public Engagement() {

    }


    public int getId() {
        return id;
    }

    public Timestamp getCreated() { return created; }

    public String getNameOfEngagement() { return nameOfEngagement; }

    public void setNameOfEngagement(String nameOfEngagement) { this.nameOfEngagement = nameOfEngagement; }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

    public int getLength() { return length; }

    public void setLength(int length) { this.length = length; }

    public int getDistance() { return distance; }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getGrains() { return grains; }

    public void setGrains(int grains) {
        this.grains = grains;
    }

    public int getWindDir() {
        return windDir;
    }

    public void setWindDir(int windDir) {
        this.windDir = windDir;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getClickValueWindage() {
        return clickValueWindage;
    }

    public void setClickValueWindage(double click_value) {
        this.clickValueWindage = click_value;
    }

    public double getClickValueElevate() {
        return clickValueElevation;
    }

    public void setClickValueElevate(double click_value) {
        this.clickValueElevation = click_value;
    }


    public int getWindagAdj() {
        return windageAdj;
    }

    public void setWindageAdj(int windage_adj) {
        this.windageAdj = windage_adj;
    }

    public int getElevationAdj() {
        return elevationAdj;
    }

    public void setElevationAdj(int elevation_adj) {
        this.elevationAdj = elevation_adj;
    }

    public TargetStyle getTargetStyle() {
        return targetStyle;
    }

    public void setTargetStyle(TargetStyle targetStyle) {
        this.targetStyle = targetStyle;
    }


}


