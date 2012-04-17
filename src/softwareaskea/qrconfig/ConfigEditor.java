package softwareaskea.qrconfig;

import softwareaskea.qrconfig.profiles.Profile;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class ConfigEditor {
	
	private BluetoothAdapter	mBluetoothAdapter;
	private WifiManager			mWifiManager;
	private AudioManager		mAudioManager;
	
    protected ConfigEditor() {}// Exists only to defeat instantiation.
    
	
	public ConfigEditor(Activity activity){
		mBluetoothAdapter	=	BluetoothAdapter.getDefaultAdapter();
		mWifiManager		=	(WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
    	mAudioManager		= 	(AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
	}
	
	/*********************  Profiles  *********************/
	
	public void applyProfile(Profile profile){
		this.setBT(profile.getBluetooth());
		this.setWifi(profile.getWifi());
		this.setVB(profile.getVibration());
		this.setMVolume(profile.getMultimedia());
		this.setRTVolume(profile.getRingtone());
		this.setAlVolume(profile.getAlarm());
		
	}
	/*********************  WiFi  *********************/
    public int setWifi (int action){
		Boolean isWifiEnabled	=	isWifiEnabled();
    	if(action==Profile.WIFI_ON && !isWifiEnabled){  
    	    mWifiManager.setWifiEnabled(true);
    	    return 1;
    	}else if(action==Profile.WIFI_OFF && isWifiEnabled){  
    	    mWifiManager.setWifiEnabled(false);
    	    return 2;
    	}
    	return 0;
    }

    public Boolean isWifiEnabled(){
    	Boolean isWifiEnabled	=	mWifiManager.isWifiEnabled();
    	return isWifiEnabled;
    }
    
	/*********************  Bluetooth  *********************/
    public int setBT (int	action){
    	Boolean isBTEnabled = isBTEnabled();
    	if (action==Profile.BLUETOOTH_ON && !isBTEnabled){
    		mBluetoothAdapter.enable();
    		return 1;
    	}else if(action==Profile.BLUETOOTH_ON && isBTEnabled){
    		mBluetoothAdapter.disable();
    		return 2;
    	}
    	return 0;
    }
    
    public Boolean isBTEnabled(){
    	Boolean isBluetoothEnabled	=	mBluetoothAdapter.isEnabled();
    	return isBluetoothEnabled;
    }

	/*********************  Vibration  *********************/
	public int setVB(int action) {
		Boolean isVBEnabled	=	isVBEnabled();
    	if(action==Profile.VIBRATION_ON && !isVBEnabled){
    		mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
    	    mWifiManager.setWifiEnabled(true);
    	    return 1;
    	}else if(action==Profile.VIBRATION_ON && isVBEnabled){
    		mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
    	    return 2;
    	}
    	return 0;
	}
	
	public Boolean isVBEnabled(){
		int vs	=	mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER);
		if(vs==AudioManager.VIBRATE_SETTING_ON)
			return true;
		else
			return false;
	}

	/*********************  Volume  *********************/
    private void setVolume(int stream,int vol){
    	mAudioManager.setStreamVolume(stream, vol, 0);
    }
    
    private int getVolume(int stream){
    	int vol	=	mAudioManager.getStreamVolume(stream);
    	return vol;
    }
    
    private int getMaxVolume(int stream){
    	int vol	=	mAudioManager.getStreamMaxVolume(stream);
    	return vol;
    }
    
    //Ringtone
    public void setRTVolume (int vol){
    	if(vol!=Profile.RINGTONE_NOT_SET)
    		setVolume(AudioManager.STREAM_RING, vol);
    }
    
    public int getRTVolume(){
    	return getVolume(AudioManager.STREAM_RING);
    }
    
    public int getRTMaxVolume(){
    	return getMaxVolume(AudioManager.STREAM_RING);
    }

    //Music-Multimedia
    public void setMVolume (int vol){
    	if(vol!=Profile.MULTIMEDIA_NOT_SET)
    		setVolume(AudioManager.STREAM_MUSIC,vol);
    }
    
    public int getMVolume(){
    	return getVolume(AudioManager.STREAM_MUSIC);
    }
    
    public int getMMaxVolume(){
    	return getMaxVolume(AudioManager.STREAM_MUSIC);
    }

    //Alarm
    public void setAlVolume (int vol){
    	if(vol!=Profile.ALARM_NOT_SET)
    		setVolume(AudioManager.STREAM_ALARM,vol);
    }
    
    public int getAlVolume(){
    	return getVolume(AudioManager.STREAM_ALARM);
    }


	public int getAlMaxVolume() {
		return getMaxVolume(AudioManager.STREAM_ALARM);
	}
    
	
    
	/*********************  GoogleChartApi QR  *********************/

	public static String getGoogleChartQRLink(int width, int height, String data){
    	String	link	=	"https://chart.googleapis.com/chart?chs="+width+"x"+height+"&cht=qr&chl="+data;
    	return link;
    }
    public static String getGoogleChartQRLink(String data){
    	String	link	=	getGoogleChartQRLink(256,256,data);
    	return link;
    }

	
}
