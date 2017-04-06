package moyett.car_mechanic_app_check_sheet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 */

public class ViewInspectionsActivity extends AppCompatActivity {
    LinearLayout root;
    DBHelper dbHelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inspections_activity);

        root = (LinearLayout) findViewById(R.id.root);

        // collect names of inspection items
        ArrayList<String> inspectionItemNames = new ArrayList<>();
        inspectionItemNames.add(getResources().getString(R.string.name));
        inspectionItemNames.add(getResources().getString(R.string.mileage));
        inspectionItemNames.add(getResources().getString(R.string.year_make_model));
        inspectionItemNames.add(getResources().getString(R.string.email));
        inspectionItemNames.add(getResources().getString(R.string.license));
        inspectionItemNames.add(getResources().getString(R.string.vin));
        inspectionItemNames.add(getResources().getString(R.string.ro));
        inspectionItemNames.add(getResources().getString(R.string.exterior_body));
        inspectionItemNames.add(getResources().getString(R.string.windshield_glass));
        inspectionItemNames.add(getResources().getString(R.string.wipers));
        inspectionItemNames.add(getResources().getString(R.string.lights_head_brake_turn));
        inspectionItemNames.add(getResources().getString(R.string.interior_lights));
        inspectionItemNames.add(getResources().getString(R.string.ac_operation));
        inspectionItemNames.add(getResources().getString(R.string.heating));
        inspectionItemNames.add(getResources().getString(R.string.comments));
        inspectionItemNames.add(getResources().getString(R.string.inspected_by));
        inspectionItemNames.add(getResources().getString(R.string.date));

        // collect references to views that hold values of inspection items
        ArrayList<Integer> inspectionItemIDs = new ArrayList<>();
        inspectionItemIDs.add(R.id.etName);
        inspectionItemIDs.add(R.id.etMileage);
        inspectionItemIDs.add(R.id.etYearMakeModel);
        inspectionItemIDs.add(R.id.etEmail);
        inspectionItemIDs.add(R.id.etLicense);
        inspectionItemIDs.add(R.id.etVIN);
        inspectionItemIDs.add(R.id.etRO);
        inspectionItemIDs.add(R.id.rgExteriorBody);
        inspectionItemIDs.add(R.id.rgWindshieldGlass);
        inspectionItemIDs.add(R.id.rgWipers);
        inspectionItemIDs.add(R.id.rgLightsHeadBrakeTurn);
        inspectionItemIDs.add(R.id.rgInteriorLights);
        inspectionItemIDs.add(R.id.rgAcOperation);
        inspectionItemIDs.add(R.id.rgHeating);
        inspectionItemIDs.add(R.id.etComments);
        inspectionItemIDs.add(R.id.etInspectedBy);
        inspectionItemIDs.add(R.id.etDate);

        // insert column headers
        LinearLayout columnHeaders = new LinearLayout(this);
//        columnHeaders.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        root.addView(columnHeaders);
        columnHeaders.setOrientation(LinearLayout.HORIZONTAL);
        for (String header : inspectionItemNames) {
            TextView textView = new TextView(this);
//            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            textView.setText(header);
            columnHeaders.addView(textView);
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 10, 50, 10);
            params.width = 200;
            textView.setLayoutParams(params);
        }

        // list out all values of saved inspections

        // get database
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();

        // read data
        Cursor cursor = db1.rawQuery("select * from inspections", null);

        int columnCount = cursor.getColumnCount();
        while(cursor.moveToNext()) {
            LinearLayout columnValues = new LinearLayout(this);
//        columnHeaders.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            columnHeaders.setOrientation(LinearLayout.HORIZONTAL);
            root.addView(columnValues);
            for (int i = 1; i < columnCount; i++) {
                String result = cursor.getString(i);

                TextView textView = new TextView(this);
//            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                textView.setText(result);
                LayoutParams params = new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                params.width = 200;

                params.setMargins(10, 10, 50, 10);
                textView.setLayoutParams(params);

                columnValues.addView(textView);
            }
        }

        cursor.close();

        // close database
        db1.close();
    }
}
