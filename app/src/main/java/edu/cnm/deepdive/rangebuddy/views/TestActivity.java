package edu.cnm.deepdive.rangebuddy.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import edu.cnm.deepdive.rangebuddy.R;
import edu.cnm.deepdive.rangebuddy.entities.TargetStyle;
import edu.cnm.deepdive.rangebuddy.helpers.DBHelper;
import edu.cnm.deepdive.rangebuddy.R;

public class TestActivity extends AppCompatActivity {

    private DBHelper dbHelper = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try {
            Dao<TargetStyle, Integer> dao = getHelper().getTargetStyleDao();
            List<TargetStyle> style = dao.queryForAll();
            ArrayAdapter<TargetStyle> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, style);
            ((ListView) findViewById(R.id.queryList)).setAdapter (adapter);
        } catch (SQLException ex) {
            Log.e("ListView", "Couldn't query database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        releaseHelper();
        super.onDestroy();
    }
}
