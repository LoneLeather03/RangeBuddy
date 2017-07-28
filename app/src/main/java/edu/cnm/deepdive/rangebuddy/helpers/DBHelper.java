package edu.cnm.deepdive.rangebuddy.helpers;

/**
 * Created by David S. Martinez on 7/27/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import edu.cnm.deepdive.rangebuddy.entities.Engagement;
import edu.cnm.deepdive.rangebuddy.entities.Shot;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;


public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "range-buddy.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Engagement, Integer> engagementDao = null;
    private RuntimeExceptionDao<Engagement, Integer> engagementRuntimeDao = null;

    private Dao<Shot, Integer> shotDao = null;
    private RuntimeExceptionDao<Shot, Integer> shotRuntimeDao = null;

    private Dao<TargetStyle, Integer> targetDao = null;
    private RuntimeExceptionDao<TargetStyle, Integer> targetRuntimeDao = null;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DBHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Engagement.class);
            TableUtils.createTable(connectionSource, Shot.class);
            TableUtils.createTable(connectionSource, TargetStyle.class);

        } catch (Exception e) {
            Log.e(DBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }




    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
   //     try {
   //         Log.i(DBHelper.class.getName(), "onUpgrade");
   //     TableUtils.dropTable(connectionSource, Engagement.class, true);
   //         TableUtils.dropTable(connectionSource, Shot.class, true);
   //         TableUtils.dropTable(connectionSource, TargetStyle.class, true);
   //         // after we drop the old databases, we create the new ones
   //         onCreate(db, connectionSource);
   //     } catch (SQLException e) {
   //         Log.e(DBHelper.class.getName(), "Can't drop databases", e);
   //         throw new RuntimeException(e);
   //     }
    }

}

