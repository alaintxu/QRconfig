package softwareaskea.qrconfig.db;

import java.util.ArrayList;
import java.util.List;

import softwareaskea.qrconfig.profiles.Profile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDAO {
	
	// Database fields
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = {
			SQLiteHelper.COLUMN_ID,
			SQLiteHelper.COLUMN_NAME,
			SQLiteHelper.COLUMN_WIFI,
			SQLiteHelper.COLUMN_BLUETOOTH,
			SQLiteHelper.COLUMN_VIBRATION,
			SQLiteHelper.COLUMN_VOLUME,
			SQLiteHelper.COLUMN_V_MULTIMEDIA};

	public ProfileDAO(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public Profile createProfile(Profile profile){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, profile.getName());
		values.put(SQLiteHelper.COLUMN_WIFI, profile.getWifi());
		values.put(SQLiteHelper.COLUMN_BLUETOOTH, profile.getBluetooth());
		values.put(SQLiteHelper.COLUMN_VIBRATION, profile.getVibration());
		values.put(SQLiteHelper.COLUMN_VOLUME, profile.getVolume());
		values.put(SQLiteHelper.COLUMN_V_MULTIMEDIA, profile.getMultimediaVolume());
		long insertId = database.insert(SQLiteHelper.TABLE_PROFILES, null,
				values);
		// To show how to query
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToProfile(cursor);
		
	}
	
	public Profile createProfile(String name,
								Boolean wifi, 
								Boolean bluetooth, 
								Boolean vibration,
								int volume,
								int multimediaVolume){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, name);
		values.put(SQLiteHelper.COLUMN_WIFI, wifi);
		values.put(SQLiteHelper.COLUMN_BLUETOOTH, bluetooth);
		values.put(SQLiteHelper.COLUMN_VIBRATION, vibration);
		values.put(SQLiteHelper.COLUMN_VOLUME, volume);
		values.put(SQLiteHelper.COLUMN_V_MULTIMEDIA, multimediaVolume);
		long insertId = database.insert(SQLiteHelper.TABLE_PROFILES, null,
				values);
		// To show how to query
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToProfile(cursor);
		
	}
	
	public void deleteProfile(Profile profile) {
		long id = profile.getId();
		database.delete(SQLiteHelper.TABLE_PROFILES, SQLiteHelper.COLUMN_ID
				+ " = " + id, null);
		System.out.println("Profile deleted with id: " + id);
	}

	public List<Profile> getAllProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		try{
			Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
					allColumns, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Profile profile = cursorToProfile(cursor);
				profiles.add(profile);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
		}catch(Exception e){
			System.out.println("Error on ProfileDAO.getAllProfiles(): "+e.getMessage());
			profiles	=	new ArrayList<Profile>();
		}
		return profiles;
	}

	public List<String> getAllProfileNames() {
		//String[]	profileNames	=	{};
		List<String>	profileNames=	new ArrayList<String>();
		List<Profile>	profiles	=	getAllProfiles();
		for(Profile profile:profiles)
			profileNames.add(profile.getName());
		return profileNames;
	}

	private Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();
		profile.setId(cursor.getLong(0));
		profile.setName(cursor.getString(1));
		profile.setBluetooth(cursor.getInt(2));
		profile.setWifi(cursor.getInt(3));
		profile.setVibration(cursor.getInt(4));
		profile.setVolume(cursor.getInt(5));
		profile.setMultimediaVolume(cursor.getInt(6));
		return profile;
	}

	public Profile loadProfile(String profileName) throws Exception{
		Profile profile = new Profile();
		
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			profile = cursorToProfile(cursor);
		}else{
			Exception e	=	new Exception("Unable to load profile (Profile name="+profileName+")");
			throw e;
		}
		cursor.close();
			
		return profile;
	}

	public Profile loadProfile(int id) throws Exception{
		Profile profile = new Profile();
		
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			profile = cursorToProfile(cursor);
		}else{
			Exception e	=	new Exception("Unable to load profile (id="+id+")");
			throw e;
		}
		cursor.close();
			
		return profile;
	}
}
