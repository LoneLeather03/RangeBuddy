package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by David S. Martinez on 7/26/2017.
 */

@DatabaseTable(tableName = "ENGAGEMENT")
public class Engagement {

    @DatabaseField(columnName = "ENGAGEMENT_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "CALIBER")
    private int caliber;

    @DatabaseField(columnName = "GRAINS")
    private int grains;

    @DatabaseField(columnName = "VELOCITY")
    private int velocity;

    @DatabaseField(columnName = "WIND_DIR")
    private int windDir;

    @DatabaseField(columnName = "WIND_SPEED")
    private int windSpeed;

    @DatabaseField(columnName = "CLICK_VALUE")
    private int clickValue;

    @DatabaseField(columnName = "ZERO")
    private int zero;

    @DatabaseField(columnName = "WINDAGE_ADJ")
    private int windageAdj;

    @DatabaseField(columnName = "ELEVATION_ADJ")
    private int elevationAdj;

    @DatabaseField(columnName = "TARGET_STYLE_ID", canBeNull = false, foreign = true)
    private TargetStyle targetStyle;

    public Engagement() {

    }


    public int getId() {
        return id;
    }

    public int getCaliber() {
        return caliber;
    }

    public void setCaliber(int caliber) {
        this.caliber = caliber;
    }

    public int getGrains() { return grains; }

    public void setGrains(int grains) {
        this.grains = grains;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getWind_dir() {
        return windDir;
    }

    public void setWindDir(int wind_dir) {
        this.windDir = wind_dir;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int wind_speed) {
        this.windSpeed = wind_speed;
    }

    public int getClickValue() {
        return clickValue;
    }

    public void setClickValue(int click_value) {
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


