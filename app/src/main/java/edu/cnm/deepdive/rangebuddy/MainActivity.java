package edu.cnm.deepdive.rangebuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.R;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       DBHelper helper = new DBHelper(getApplicationContext());
        helper.getWritableDatabase().close();



//        try {
//            Dao<TargetStyle, Integer> targetDao = helper.getDao(TargetStyle.class);
////            TargetStyle target = new TargetStyle();
////            target.setStyle("NRA 600yd MR-1");
////            target.setHeight(64);
////            target.setWidth(72);
////            //   } catch (SQLException ex) {
////            //      ex.printStackTrace();
////
////            targetDao.create(target);
//
//            QueryBuilder<TargetStyle, Integer> builder = targetDao.queryBuilder();
//            List<TargetStyle> result = targetDao.query(builder.prepare());
//            for (TargetStyle t : result) {
//                Log.d("database", t.getStyle());
//
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }

        setContentView(R.layout.activity_main);
    }
}
