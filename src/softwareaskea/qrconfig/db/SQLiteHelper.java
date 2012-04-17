package softwareaskea.qrconfig.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_PROFILES = "profiles";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_WIFI = "wifi";
	public static final String COLUMN_BLUETOOTH = "bluetooth";
	public static final String COLUMN_VIBRATION = "vibration";
	public static final String COLUMN_RINGTONE = "ringtone";
	public static final String COLUMN_MULTIMEDIA = "multimedia";
	public static final String COLUMN_ALARM = "alarm";

	private static final String DATABASE_NAME = "qrconfig_profile.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
		private static final String DATABASE_CREATE = "create table "
				+ TABLE_PROFILES + "( " + 
				COLUMN_ID				+ " integer primary key autoincrement, " + 
				COLUMN_NAME				+ " text not null, " +
				COLUMN_WIFI 			+ " integer, " +
				COLUMN_BLUETOOTH 		+ " integer, " +
				COLUMN_VIBRATION 		+ " integer, " +
				COLUMN_RINGTONE 		+ " integer, " +
				COLUMN_MULTIMEDIA 		+ " integer, " +
				COLUMN_ALARM	 		+ " integer" +
				");";
	
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
