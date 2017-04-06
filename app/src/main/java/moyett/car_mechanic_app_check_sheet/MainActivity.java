package moyett.car_mechanic_app_check_sheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Jonathan Moyett
 */

public class MainActivity extends AppCompatActivity {
    Button btnCreateNewInspection;
    Button btnViewOldInspections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnCreateNewInspection = (Button)findViewById(R.id.btnCreateNewInspection);
        btnViewOldInspections = (Button)findViewById(R.id.btnViewOldInspections);

        // create new inspection
        btnCreateNewInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // view old inspections
        btnViewOldInspections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewInspectionsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
}
