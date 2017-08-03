package edu.cnm.deepdive.rangebuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    private int direction = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper helper = new DBHelper(getApplicationContext());
        helper.getWritableDatabase().close();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent dbmanager = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        Button listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent listActivity = new Intent(MainActivity.this, TestActivity.class);
                startActivity(listActivity);
            }
        });

//        Button nW = (Button) findViewById(R.id.northwest);
//        nW.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                direction = 315;
//            }
//        });
        ImageView iv = (ImageView) findViewById(R.id.Compass);
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = v.getHeight();
                int width = v.getWidth();
                float xCenter = width / 2.0f;
                float yCenter = height / 2.0f;
                float deltaX = event.getX() - xCenter;
                float deltaY = event.getY() - yCenter;
                double r = Math.hypot(deltaX, deltaY);
                double speed = 60 * Math.min(2 * r / width, 1);
                double angle = 180 / Math.PI * Math.atan2(deltaX, -deltaY);

                Log.d("Compass click", "Location: " + event.getX() + " , " + event.getY());
                Log.d("Compass click", String.format("Vector: (%f, %f)%n", speed, angle));

                return true;
            }
        });

    }

}
