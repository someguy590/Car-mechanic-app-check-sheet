package moyett.car_mechanic_app_check_sheet;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);
    EditText etDate;
    Button btnInspectionDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        etDate = (EditText)findViewById(R.id.etDate);
        btnInspectionDone = (Button)findViewById(R.id.btnInspectionDone);

        // collect names of inspection items
        final ArrayList<String> inspectionItemNames = new ArrayList<>();
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.name));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.mileage));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.year_make_model));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.email));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.license));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.vin));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.ro));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.exterior_body));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.windshield_glass));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.wipers));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.lights_head_brake_turn));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.interior_lights));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.ac_operation));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.heating));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.comments));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.inspected_by));
        inspectionItemNames.add(getResources().getResourceEntryName(R.string.date));

        // collect references to views that hold values of inspection items
        final ArrayList<Integer> inspectionItemIDs = new ArrayList<>();
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

        // open date picker dialog
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                    return;

                // display date picker dialog
                selectDateDialog();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display date picker dialog
                selectDateDialog();
            }
        });

        btnInspectionDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get values to inspection items
                ArrayList<String> inspectionItemValues = new ArrayList<>();
                for (int id : inspectionItemIDs) {
                    View v = findViewById(id);

                    if (v instanceof EditText) {
                        EditText item = (EditText)v;
                        inspectionItemValues.add(item.getText().toString());
                    }

                    if (v instanceof RadioGroup) {
                        RadioGroup item = (RadioGroup)v;

                        for (int i = 0; i < item.getChildCount(); i++) {
                            View itemChild = item.getChildAt(i);
                            if (!(itemChild instanceof RadioButton))
                                continue;
                            RadioButton rb = (RadioButton)itemChild;

                            if (((RadioButton)item.getChildAt(i)).isChecked()) {
                                boolean isGreen = rb.getBackground().getConstantState().equals(getResources().getDrawable(R.color.green, null).getConstantState());
                                boolean isYellow = rb.getBackground().getConstantState().equals(getResources().getDrawable(R.color.yellow, null).getConstantState());

                                if (isGreen)
                                    inspectionItemValues.add(getResources().getString(R.string.general_color_code_green));
                                else if (isYellow)
                                    inspectionItemValues.add(getResources().getString(R.string.general_color_code_yellow));
                                else
                                    inspectionItemValues.add(getResources().getString(R.string.general_color_code_red));
                            }
                        }
                    }
                }

                // get database
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // insert values
                ContentValues row = new ContentValues();

                for (int i = 0; i < inspectionItemNames.size(); i++)
                    row.put(inspectionItemNames.get(i), inspectionItemValues.get(i));
                db.insert("inspections", null, row);

                // close database
                db.close();

                // get database
                SQLiteDatabase db1 = dbHelper.getReadableDatabase();

                // read data
                Cursor cursor = db1.rawQuery("select * from inspections", null);


                int columnCount = cursor.getColumnCount();
                System.out.println(Arrays.toString(cursor.getColumnNames()));
                System.out.println();

                while(cursor.moveToNext()) {
                    for (int i = 0; i < columnCount; i++) {
                        String result = cursor.getString(i);
                        System.out.print(result + " ");
                    }
                    System.out.println();
                }

                cursor.close();

                // close database
                db1.close();

                // return to main menu
                Intent intent = new Intent(FormActivity.this, MainActivity.class);
                FormActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    // bring up date picker dialog when selecting date
    private void selectDateDialog() {
        // get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // instantiate date picker dialog with current date chosen and display
        DatePickerDialog datePicker = new DatePickerDialog(FormActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        etDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                    }
                }, year, month, dayOfMonth);
        datePicker.show();
    }
}

