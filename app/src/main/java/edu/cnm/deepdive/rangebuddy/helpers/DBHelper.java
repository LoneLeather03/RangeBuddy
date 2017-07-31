package edu.cnm.deepdive.rangebuddy.helpers;

/**
 * Created by David S. Martinez on 7/27/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.cnm.deepdive.rangebuddy.entities.Engagement;
import edu.cnm.deepdive.rangebuddy.entities.Shot;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;

import static android.R.attr.width;
import static edu.cnm.deepdive.rangebuddy.R.attr.height;


public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "range-buddy.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Engagement, Integer> engagementDao = null;
    private RuntimeExceptionDao<Engagement, Integer> engagementRuntimeDao = null;

    private Dao<Shot, Integer> shotDao = null;
    private RuntimeExceptionDao<Shot, Integer> shotRuntimeDao = null;

    private Dao<TargetStyle, Integer> targetDao = null;
    private RuntimeExceptionDao<TargetStyle, Integer> targetRuntimeDao = null;

    private static ConnectionSource source;

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
            source = connectionSource;
            TableUtils.createTable(connectionSource, Engagement.class);
            TableUtils.createTable(connectionSource, Shot.class);
            TableUtils.createTable(connectionSource, TargetStyle.class);
            populateDB();
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

        public void populateDB() throws SQLException {
            TargetStyle style = new TargetStyle();
            style.setStyle("NRA 600yd MR-1");
            style.setHeight(64);
            style.setWidth(72);
            TargetStyleDao.getInstance().create(style);
            style = new TargetStyle();
            style.setStyle("NRA 500yd MR-65");
            style.setHeight(37);
            style.setWidth(37);
            TargetStyleDao.getInstance().create(style);
            style = new TargetStyle();





        }


    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;

        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

    public static class TargetStyleDao{

        private static class Helper {

            private static final Dao<TargetStyle, Integer> INSTANCE;

            static {
            try {
               INSTANCE = DaoManager.createDao(source, TargetStyle.class);
                } catch (SQLException e) {
                throw new RuntimeException();
                }
            }
        }
        public static Dao <TargetStyle, Integer> getInstance() {
            return Helper.INSTANCE;
        }
    }

    public static class EngagementDao{

        private static class Helper {

            private static final Dao<Engagement, Integer> INSTANCE;

            static {
                try {
                    INSTANCE = DaoManager.createDao(source, Engagement.class);
                } catch (SQLException e) {
                    throw new RuntimeException();
                }

            }
        }

        public static Dao <Engagement, Integer> getInstance() {
            return Helper.INSTANCE;
        }
    }

    public static class ShotDao{

        private static class Helper {

            private static final Dao<Shot, Integer> INSTANCE;

            static {
                try {
                    INSTANCE = DaoManager.createDao(source, Shot.class);
                } catch (SQLException e) {
                    throw new RuntimeException();
                }

            }
        }

        public static Dao <Shot, Integer> getInstance() {
            return Helper.INSTANCE;
        }
    }

}

