package edu.cnm.deepdive.rangebuddy.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by David S. Martinez on 7/26/2017.
 */
@DatabaseTable(tableName = "TARGET_STYLE")
public class TargetStyle {

    @DatabaseField(columnName = "TARGET_STYLE_ID", generatedId = true)
    private int id;

    @DatabaseField(columnName = "STYLE", width = 255)
    private String style;

    @DatabaseField(columnName = "HEIGHT")
    private int height;

    @DatabaseField(columnName = "WIDTH")
    private int width;

    @DatabaseField(columnName = "IMAGE")
    private String image;

    @Override
    public String toString() {
        return getStyle();
    }

    @ForeignCollectionField(eager = false )
    private ForeignCollection<Engagement> engagements;

    public TargetStyle() {

    }

    public int getId() {
        return id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

}
