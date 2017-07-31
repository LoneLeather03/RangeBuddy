package edu.cnm.deepdive.rangebuddy.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.R;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;
import edu.cnm.deepdive.rangebuddy.rangebuddy.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try {
            List<TargetStyle> style = DBHelper.TargetStyleDao.getInstance().query(
                    DBHelper.TargetStyleDao.getInstance().queryBuilder().prepare());
            ArrayAdapter<TargetStyle> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, style);
            ((ListView) findViewById(R.id.queryList)).setAdapter (adapter);
        } catch (SQLException e) {
            Log.e("ListView", "Couldn't query database: " + ex.getMessage());
            e.printStackTrace();
        }

    }
}
