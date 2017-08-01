package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by David S. Martinez on 7/26/2017.
 */

@DatabaseTable(tableName = "ENGAGEMENT")
public class Engagement {

    @DatabaseField(columnName = "ENGAGEMENT_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "CALIBER")
    private String caliber;

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

    @DatabaseField(columnName = "CLICK_VALUE")
    private double clickValue;

    @DatabaseField(columnName = "ZERO")
    private int zero;

    @DatabaseField(columnName = "WINDAGE_ADJ")
    private int windageAdj;

    @DatabaseField(columnName = "ELEVATION_ADJ")
    private int elevationAdj;

    @DatabaseField(columnName = "TARGET_STYLE_ID", canBeNull = false, foreign = true)
    private TargetStyle targetStyle;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<Shot> shots;

    public Engagement() {

    }


    public int getId() {
        return id;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }

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

    public double getClickValue() {
        return clickValue;
    }

    public void setClickValue(double click_value) {
        this.clickValue = click_value;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
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


