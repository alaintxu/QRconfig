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
			SQLiteHelper.COLUMN_RINGTONE,
			SQLiteHelper.COLUMN_MULTIMEDIA,
			SQLiteHelper.COLUMN_ALARM};

	public ProfileDAO(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		if(database==null) database = dbHelper.getWritableDatabase();
	}

	public void close() {
		if(database!=null){
			dbHelper.close();
			database=null;
		}
	}
	
	public Profile createProfile(Profile profile){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, profile.getName());
		values.put(SQLiteHelper.COLUMN_WIFI, profile.getWifi());
		values.put(SQLiteHelper.COLUMN_BLUETOOTH, profile.getBluetooth());
		values.put(SQLiteHelper.COLUMN_VIBRATION, profile.getVibration());
		values.put(SQLiteHelper.COLUMN_RINGTONE, profile.getRingtone());
		values.put(SQLiteHelper.COLUMN_MULTIMEDIA, profile.getMultimedia());
		values.put(SQLiteHelper.COLUMN_ALARM, profile.getAlarm());
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
								Boolean gps, 
								Boolean vibration,
								int ringtone,
								int multimedia,
								int alarm){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMN_NAME, name);
		values.put(SQLiteHelper.COLUMN_WIFI, wifi);
		values.put(SQLiteHelper.COLUMN_BLUETOOTH, bluetooth);
		values.put(SQLiteHelper.COLUMN_VIBRATION, vibration);
		values.put(SQLiteHelper.COLUMN_RINGTONE, ringtone);
		values.put(SQLiteHelper.COLUMN_MULTIMEDIA, multimedia);
		values.put(SQLiteHelper.COLUMN_ALARM, alarm);
		long insertId = database.insert(SQLiteHelper.TABLE_PROFILES, null,
				values);
		// To show how to query
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToProfile(cursor);
		
	}

	
	/**
	 * Delete profile
	 * @param profile profile to delete
	 */
	public int deleteProfile(Profile profile) {
		long id = profile.getId();
		return deleteProfile(id);
	}
	
	/**
	 * Delete profile
	 * @param id identification of the profile to delete
	 * @return 
	 */
	public int deleteProfile(long id) {
		open();
		return database.delete(SQLiteHelper.TABLE_PROFILES, SQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Profile> getAllProfiles() {
		open();
		
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
		List<String>	profileNames=	new ArrayList<String>();
		List<Profile>	profiles	=	getAllProfiles();
		for(Profile profile:profiles)
			profileNames.add(profile.getName());
		return profileNames;
	}

	private Profile cursorToProfile(Cursor cursor) {
		int i=0;
		Profile profile = new Profile();
		
		profile.setId(cursor.getLong(i));
		i++;
		
		profile.setName(cursor.getString(i));
		i++;
		
		profile.setBluetooth(cursor.getInt(i));
		i++;
		
		profile.setWifi(cursor.getInt(i));
		i++;
		
		profile.setVibration(cursor.getInt(i));
		i++;
		
		profile.setRingtone(cursor.getInt(i));
		i++;
		
		profile.setMultimedia(cursor.getInt(i));
		i++;
		
		profile.setAlarm(cursor.getInt(i));
		i++;
		
		return profile;
	}

	public Profile loadProfile(String profileName){
		open();
		
		Profile profile = new Profile();
		
		Cursor cursor = database.query(SQLiteHelper.TABLE_PROFILES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast())
			profile = cursorToProfile(cursor);
		cursor.close();
			
		return profile;
	}

	public Profile loadProfile(int id) throws Exception{
		open();
		
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
