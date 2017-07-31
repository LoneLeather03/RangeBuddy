package edu.cnm.deepdive.rangebuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.R;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.AndroidDatabaseManager;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;
import edu.cnm.deepdive.rangebuddy.views.CustomDrawableView;
import edu.cnm.deepdive.rangebuddy.views.TestActivity;

public class MainActivity extends AppCompatActivity {

    CustomDrawableView mCustomDrawableView;
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




        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });



        Button listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
                Intent listActivity = new Intent(MainActivity.this, TestActivity.class);
                startActivity(listActivity);
        }
    });

    }
}
