package edu.cnm.deepdive.rangebuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.entities.Engagement;
import edu.cnm.deepdive.rangebuddy.entities.Shot;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;
import edu.cnm.deepdive.rangebuddy.views.TestActivity;


import static java.lang.Math.round;
import static java.lang.Math.sin;
// , SeekBar.OnSeekBarChangeListener code for seekbars that were taken out
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final double TOLERANCE = 0.1d;
    //    private static final char[] ARROWS = {'\u21D1', '\u21D7', '\u21D2', '\u21D8', '\u21D3', '\u21D9', '\u21D0', '\u21D6'};
    private int direction = 0;
    ArrayList<float[]> shots = new ArrayList<>();
    ArrayList<String> grains = new ArrayList<>();
    TargetStyle currentStyle = null;
    Engagement currentWeight = null;
    double speed = 0;
    double angle = 0;
    double windFactor = 0;
    int range = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getHelper().getWritableDatabase().close();
        fuckYou();
        loadNameSpinner();
        loadDistanceSpinner();

        Button button = (Button) findViewById(R.id.DBMButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent dbmanager = new Intent(MainActivity.this, AndroidDatabaseManager.class);
//                startActivity(dbmanager);
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
                try {
                    Dao<Engagement, Integer> engagementDao = getHelper().getEngagementDao();
                    Dao<Shot, Integer> shotDao = getHelper().getShotDao();
                    Engagement engagement = new Engagement();
                    EditText name = (EditText) findViewById(R.id.name);
                    engagement.setNameOfEngagement(name.getText().toString());
                    Spinner distanceSpinner = (Spinner) findViewById(R.id.distance);
                    engagement.setDistance(Integer.parseInt(distanceSpinner.getSelectedItem().toString()));
                    //TODO fill in remaining engagement fields

                    RadioButton length16 = (RadioButton) findViewById(R.id.length16);
                    if (length16.isChecked()) {
                        engagement.setLength(16);
                    } else {
                        engagement.setLength(20);
                    }
                    ImageView compass = (ImageView) findViewById(R.id.Compass);
                    engagement.setWindDir((int)Math.round(angle));
                    engagement.setWindSpeed((int) speed);
//                    SeekBar rawWindage = ((SeekBar) findViewById(R.id.windageValue));
//                    double realWindage = rawWindage.getProgress() / 100d + 1;
//                    engagement.setClickValueWindage(realWindage);
//                    SeekBar rawElevation = ((SeekBar) findViewById(R.id.elevationValue));
//                    double realElevation = rawElevation.getProgress() / 100d + 1;
//                    engagement.setClickValueElevate(realElevation);
                    Spinner style = (Spinner) findViewById(R.id.targetStyle);
                    engagement.setTargetStyle(currentStyle);
                    engagementDao.create(engagement);
                    for (int i = 0; i < shots.size(); i++) {
                        float[] shotCoordinates = shots.get(i);
                        Shot shot = new Shot();
                        shot.setEngagement(engagement);
                        shot.setxOffset(shotCoordinates[0]);
                        shot.setyOffset(shotCoordinates[1]);
                        shot.setSequence(i);
                        shotDao.create(shot);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
                loadNameSpinner();

            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

//Decided not to use these seekbars
//        ((SeekBar) findViewById(R.id.windageValue)).setOnSeekBarChangeListener(this);
//
//        ((SeekBar) findViewById(R.id.elevationValue)).setOnSeekBarChangeListener(this);

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
                speed = 30 * Math.min(2 * r / width, 1);
                angle = 180 / Math.PI * Math.atan2(deltaX, -deltaY);
                windFactor = Math.pow(Math.sin(angle * Math.PI / 180), 2);
                updateClickDisplay();

//                int arrowIndex = ((int) Math.round(angle / 45) + 8) % 8;

                //TODO Figure out which character to use.
                Bitmap base = BitmapFactory.decodeResource(getResources(), R.drawable.clock6);
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
                values.setText(String.format("touch clockface to set values: %n drag line for better accuracy %n wind speed:  wind direction: %n (Mph %.2f, Direction %.2f)%n", speed, angle));

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
                //TODO iterate over shots to see if loadDistanceSpinner between any shot and xOffset yOffset less than some tolerance.

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

        ((Spinner)findViewById(R.id.distance)).setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                range = Integer.parseInt((String) parent.getSelectedItem());
                updateClickDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        ((RadioGroup)findViewById(R.id.length)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                updateClickDisplay();
            }
        });
    }

    private void updateClickDisplay() {
        double suggestedAdj = range * speed * windFactor / 1000;
        if (((RadioButton) findViewById(R.id.length20)).isChecked()) {
            suggestedAdj = suggestedAdj * 1.75;
        } else {
            suggestedAdj = suggestedAdj * .75 * 1.75;
        }
        ((EditText) findViewById(R.id.editText)).setText(String.format("%.2f", suggestedAdj));

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
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }

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

//    protected void weight() {
//
//        Spinner saved = (Spinner) findViewById(R.id.savedEngagements);
//        String[] weights = getResources().getStringArray(R.array.bulletWeights);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, weights);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        saved.setAdapter(adapter);
//    }

    protected void loadDistanceSpinner() {

        Spinner distance = (Spinner) findViewById(R.id.distance);
        String[] distances = getResources().getStringArray(R.array.distances);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, distances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distance.setAdapter(adapter);
        range = Integer.parseInt(distances[0]);
    }

    private void loadNameSpinner() {

        Spinner saved = (Spinner) findViewById(R.id.savedEngagements);
        try {
            Dao<Engagement, Integer> engagementDao = getHelper().getEngagementDao();
            QueryBuilder<Engagement, Integer> builder = engagementDao.queryBuilder();
            builder.orderBy("CREATED", false).orderBy("ENGAGEMENT_NAME", true);
            List<Engagement> engagements = engagementDao.query(builder.prepare());
            ArrayAdapter<Engagement> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, engagements);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            saved.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onStop() {
        releaseHelper();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getHelper();
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
            case R.id.length16:
                if (checked)
                    break;
            case R.id.length20:
                if (checked)
                    break;
        }
    }

    // Code for seekbars that I decided not to use
//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        switch (seekBar.getId()) {
//            case R.id.windageValue:
//                TextView view = (TextView) findViewById(R.id.windageValueDisplay);
//                view.setText(String.format("%3.2f", (progress + 100) / 100f));
//                break;
//            case R.id.elevationValue:
//                TextView view1 = (TextView) findViewById(R.id.elevationValueDisplay);
//                view1.setText(String.format("%3.2f", (progress + 100) / 100f));
//                break;
//        }
//
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//
//    }

}



