package edu.cnm.deepdive.rangebuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.entities.Engagement;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.AndroidDatabaseManager;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;
import edu.cnm.deepdive.rangebuddy.views.TestActivity;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private static final double TOLERANCE = 0.1d;
    //    private static final char[] ARROWS = {'\u21D1', '\u21D7', '\u21D2', '\u21D8', '\u21D3', '\u21D9', '\u21D0', '\u21D6'};
    private int direction = 0;
    ArrayList<float[]> shots = new ArrayList<>();
    ArrayList<String> grains = new ArrayList<>();
    TargetStyle currentStyle = null;
    Engagement currentWeight = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getHelper().getWritableDatabase().close();
        fuckYou();
        weight();
        distance();

        Button button = (Button) findViewById(R.id.DBMButton);
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

        Button saveButton = (Button) findViewById(R.id.saveEngagement);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        Button viewButton = (Button) findViewById(R.id.viewEngagements);
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        final ImageView iv = (ImageView) findViewById(R.id.Compass);


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
                double speed = 30 * Math.min(2 * r / width, 1);
                double angle = 180 / Math.PI * Math.atan2(deltaX, -deltaY);

//                int arrowIndex = ((int) Math.round(angle / 45) + 8) % 8;


                //TODO Figure out which character to use.
                Bitmap base = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
                Bitmap.Config config = base.getConfig();
                int baseWidth = base.getWidth();
                int baseHeight = base.getHeight();
                float scaledXcenter = iv.getScaleX() * xCenter;
                float scaledYcenter = iv.getScaleY() * yCenter;

                Bitmap newImage = Bitmap.createBitmap(baseWidth, baseHeight, config);
                Canvas c = new Canvas(newImage);
                c.drawBitmap(base, 0, 0, null);


                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(10.0f);

                paint.setTextSize(60);
//                c.drawText(ARROWS, arrowIndex, 1, event.getX() + 39, event.getY() + 39, paint);
//                Drawable arrowhead = ResourcesCompat.getDrawable(getResources(), R.drawable.arrowhead, null);
//                arrowhead.setBounds(0, 0, 30, 30);
////                c.translate(event.getX(), event.getY());
//                arrowhead.draw(c);
//                c.translate(-event.getX(), -event.getY());
                c.scale(iv.getScaleX(), iv.getScaleY());
                c.drawLine(c.getWidth() / 2.0f, c.getHeight() / 2.0f, c.getWidth() * event.getX() / width, c.getHeight() * event.getY() / height, paint);
                iv.setImageBitmap(newImage);
//                Log.d("Compass click", "Location: " + event.getX() + " , " + event.getY());
//                Log.d("Compass click", String.format("Vector: (%f, %f)%n", speed, angle));
                TextView values = (TextView) findViewById(R.id.dirSpeedValues);
                values.setText(String.format("Touch compass for values: %n Drag line for better accuracy %n wind speed:  wind direction: %n (Mph %.2f, Direction %.2f)%n", speed, angle));

                return true;
            }
        });

        final ImageView targetView = (ImageView) findViewById(R.id.target);

        targetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = v.getHeight();
                int width = v.getWidth();
                float xShot = event.getX();
                float yShot = event.getY();
                float xOffset = (xShot / width) - 0.5f;
                float yOffset = (yShot / height) - 0.5f;
                //TODO iterate over shots to see if distance between any shot and xOffset yOffset less than some tolerance.

                Switch removeMode = (Switch) findViewById(R.id.removeSwitch);
                boolean inRemoveMode = removeMode.isChecked();

                if (inRemoveMode) {
                    double closestDistance = Double.MAX_VALUE;
                    float[] shotToRemove = null;
                    for (float[] shot : shots) {
                        double d = Math.hypot(xOffset - shot[0], yOffset - shot[1]);
                        if (d < TOLERANCE && d < closestDistance) {
                            closestDistance = d;
                            shotToRemove = shot;
                        }
                    }
                    if (shotToRemove != null) {
                        shots.remove(shotToRemove);
                    }
                } else {
                    shots.add(new float[]{xOffset, yOffset});
                }
                float xCenter = width / 2.0f;
                float yCenter = height / 2.0f;
                float deltaX = event.getX() - xCenter;
                float deltaY = event.getY() - yCenter;
                double r = Math.hypot(deltaX, deltaY);
//                double speed = 30 * Math.min(2 * r / width, 1);
//                double angle = 180 / Math.PI * Math.atan2(deltaX, -deltaY);

                Bitmap base = getBitmap(currentStyle.getImage());
                Bitmap.Config config = base.getConfig();
                int baseWidth = base.getWidth();
                int baseHeight = base.getHeight();
                float scaledXcenter = targetView.getScaleX() * xCenter;
                float scaledYcenter = targetView.getScaleY() * yCenter;

                Bitmap newImage = Bitmap.createBitmap(baseWidth, baseHeight, config);
                Canvas c = new Canvas(newImage);
                c.drawBitmap(base, 0, 0, null);

                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
//                paint.setStrokeWidth(10);
//                c.scale(targetView.getScaleX(), targetView.getScaleY());
                for (float[] shot : shots) {
                    c.drawOval(c.getWidth() * (shot[0] + 0.5f) - 5,
                            c.getHeight() * (shot[1] + 0.5f) - 5,
                            c.getWidth() * (shot[0] + 0.5f) + 5,
                            c.getHeight() * (shot[1] + 0.5f) + 5, paint);
                }

                targetView.setImageBitmap(newImage);

                return true;

            }
        });

        ((SeekBar) findViewById(R.id.windageValue)).setOnSeekBarChangeListener(this);

        ((SeekBar) findViewById(R.id.elevationValue)).setOnSeekBarChangeListener(this);

    }

    DBHelper dbHelper = null;

    private synchronized DBHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        }
        return dbHelper;
    }

    private synchronized void releaseHelper() {
        if (dbHelper != null) {
            releaseHelper();
            dbHelper = null;
        }
    }


    //        Log.d("ListView", "onCreate");
//        setContentView(R.layout.simple_spinner_item);
    protected void fuckYou() {
        try {
            Dao<TargetStyle, Integer> dao = getHelper().getTargetStyleDao();
            List<TargetStyle> style = dao.queryForAll();
            currentStyle = style.get(0);
            ImageView iv = (ImageView) findViewById(R.id.target);
            iv.setImageBitmap(getBitmap(currentStyle.getImage()));
            ArrayAdapter<TargetStyle> adapter = new ArrayAdapter<>(this,
                    R.layout.simple_spinner_item, style);
            Spinner spinner = (Spinner) findViewById(R.id.targetStyle);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        } catch (Exception ex) {
            Log.e("ListView", "Couldn't query database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void weight() {

        Spinner bulletWeight = (Spinner) findViewById(R.id.bulletWeight);
        String[] weights = getResources().getStringArray(R.array.bulletWeights);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, weights);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bulletWeight.setAdapter(adapter);
    }

    protected void distance() {

        Spinner bulletWeight = (Spinner) findViewById(R.id.distance);
        String[] distances = getResources().getStringArray(R.array.distances);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, distances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bulletWeight.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        releaseHelper();
        super.onDestroy();
    }

    private Bitmap getBitmap(String name) {
        int resourceID = getResources().getIdentifier(name, "drawable", getPackageName());
        return BitmapFactory.decodeResource(getResources(), resourceID);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        currentStyle = (TargetStyle) parent.getItemAtPosition(pos);
        String image = currentStyle.getImage();
        ImageView iv = (ImageView) findViewById(R.id.target);
        iv.setImageBitmap(getBitmap(image));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.length18:
                if (checked)
                    break;
            case R.id.length20:
                if (checked)
                    break;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.windageValue:
                TextView view = (TextView) findViewById(R.id.windageValueDisplay);
                view.setText(String.format("%3.2f", (progress + 100) / 100f));
                break;
            case R.id.elevationValue:
                TextView view1 = (TextView) findViewById(R.id.elevationValueDisplay);
                view1.setText(String.format("%3.2f", (progress + 100) / 100f));
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }



    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



}



