package moyett.car_mechanic_app_check_sheet;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    public DBHelper(Context context) {
        super(context, "inspectionsDB", null, 1);
        this.context = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table inspections (" +
                "id integer primary key, " +
                context.getResources().getResourceEntryName(R.string.name) + " text, " +
                context.getResources().getResourceEntryName(R.string.mileage) + " text, " +
                context.getResources().getResourceEntryName(R.string.year_make_model) + " text, " +
                context.getResources().getResourceEntryName(R.string.email) + " text, " +
                context.getResources().getResourceEntryName(R.string.license) + " text, " +
                context.getResources().getResourceEntryName(R.string.vin) + " text, " +
                context.getResources().getResourceEntryName(R.string.ro) + " text, " +

                context.getResources().getResourceEntryName(R.string.exterior_body) + " text, " +
                context.getResources().getResourceEntryName(R.string.windshield_glass) + " text, " +
                context.getResources().getResourceEntryName(R.string.wipers) + " text, " +
                context.getResources().getResourceEntryName(R.string.lights_head_brake_turn) + " text, " +
                context.getResources().getResourceEntryName(R.string.interior_lights) + " text, " +
                context.getResources().getResourceEntryName(R.string.ac_operation) + " text, " +
                context.getResources().getResourceEntryName(R.string.heating) + " text, " +

                context.getResources().getResourceEntryName(R.string.comments) + " text, " +
                context.getResources().getResourceEntryName(R.string.inspected_by) + " text, " +
                context.getResources().getResourceEntryName(R.string.date) + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist inspections");
        onCreate(db);
    }
}
