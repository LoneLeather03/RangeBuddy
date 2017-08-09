package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by David S. Martinez on 7/26/2017.
 */


@DatabaseTable(tableName = "SHOT")
public class Shot {

    @DatabaseField(columnName = "SHOT_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "X_COORDINATE")
    private float xOffset;

    @DatabaseField(columnName = "Y_COORDINATE")
    private float yOffset;

    @DatabaseField(columnName = "SEQUENCE")
    private int sequence;

    @DatabaseField(columnName = "ENGAGEMENT_ID", canBeNull = false, foreign = true)
    private Engagement engagement;

    public Shot() {

    }

    public int getId() {
        return id;
    }

    public float getOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
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
