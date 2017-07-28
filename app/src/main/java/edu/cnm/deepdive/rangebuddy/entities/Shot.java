package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by David S. Martinez on 7/26/2017.
 */


@DatabaseTable(tableName = "SHOT")
public class Shot {

    @DatabaseField(columnName = "SHOT_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "X_COORDINATE")
    private int xCoordinate;

    @DatabaseField(columnName = "Y_COORDINATE")
    private int yCoordinate;

    @DatabaseField(columnName = "SEQUENCE")
    private int sequence;

    @DatabaseField(columnName = "ENGAGEMENT_ID", canBeNull = false, foreign = true)
    private Engagement engagement;

    public Shot() {

    }

    public int getId() {
        return id;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int x_coordinate) {
        this.xCoordinate = x_coordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int y_coordinate) {
        this.yCoordinate = y_coordinate;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Engagement getEngagement() {
        return engagement;
    }

    public void setEngagement(Engagement engagement) {
        this.engagement = engagement;
    }

}
