package softwareaskea.qrconfig;

import softwareaskea.qrconfig.db.DataBaseManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

public class ConfigEditor {
	private BluetoothAdapter	mBluetoothAdapter;
	private WifiManager			mWifiManager;
	private AudioManager		mAudioManager;
	private Activity			mActivity;
	private DataBaseManager		mDataBaseManager;
	
	public ConfigEditor(){}
	
	public ConfigEditor(Activity activity){
		mBluetoothAdapter	=	BluetoothAdapter.getDefaultAdapter();
		mWifiManager		=	(WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		mDataBaseManager	=	new	DataBaseManager(activity.getApplicationContext());
    	mAudioManager		= 	(AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		mActivity			=	activity;
	}
	
	/*********************  Bluetooth  *********************/
    public int setBT (String activate){
    	Boolean isBTEnabled = isBTEnabled();
    	if (activate.equals("1") && !isBTEnabled){
    		mBluetoothAdapter.enable();
    		return 1;
    	}else if(activate.equals("0") && isBTEnabled){
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
	public int setVB(String activate) {
		Boolean isVBEnabled	=	isVBEnabled();
    	if(activate.equals("1") && !isVBEnabled){
    		mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
    	    mWifiManager.setWifiEnabled(true);
    	    return 1;
    	}else if(activate.equals("0") && isVBEnabled){
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
    

	/*********************  WiFi  *********************/
    public int setWifi (String activate){
		Boolean isWifiEnabled	=	isWifiEnabled();
    	if(activate.equals("1") && !isWifiEnabled){  
    	    mWifiManager.setWifiEnabled(true);
    	    return 1;
    	}else if(activate.equals("0") && isWifiEnabled){  
    	    mWifiManager.setWifiEnabled(false);
    	    return 2;
    	}
    	return 0;
    }

    public Boolean isWifiEnabled(){
    	Boolean isWifiEnabled	=	mWifiManager.isWifiEnabled();
    	return isWifiEnabled;
    }

	/*********************  Volume  *********************/
    public void setVolume(int stream,int vol){
    	mAudioManager.setStreamVolume(stream, vol, 0);
    }
    
    //Ringtone
    public void setRTVolume (int vol){
    	setVolume(AudioManager.STREAM_RING, vol);
    }
    
    //Notifications
    public void setNVolume (int vol){
    	setVolume(AudioManager.STREAM_NOTIFICATION,vol);
    }

    //Music-Multimedia
    public void setMVolume (int vol){
    	setVolume(AudioManager.STREAM_MUSIC,vol);
    }
    
    private void processProfile(String profileName) {
    	ConfigProfile profile	=	mDataBaseManager.getProfile(profileName);
    	mDataBaseManager.saveConfigProfile(profile);
	}
    
	/*********************  QR Code  *********************/
    
    /**
     * Call Barcode Scanner Intent to manage QR codes
     */
    public void scanQrCode(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        mActivity.startActivityForResult(intent, 0);
    }
    
    /**
     * Manage result of QR code scan
     * @param requestCode	0 if there is any result
     * @param resultCode	the type of result
     * @param intent	Barcode Scanner Intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                if(format.equals("QR_CODE"))
                	processQr(contents);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
    
    public int processQr(String qr){
    	String pattern	=	"MCONFIG:(\\S+:\\S+;)*;";
    	if(qr.matches(pattern)){
    		qr = qr.replace("MCONFIG:","");
    		qr = qr.replace(";;","");
    		String[] split = qr.split(";");
    		for (String s : split) {
    		      String[] subsplit = s.split(":");
    		      if(subsplit[0].equals("B"))
    		    	  setBT(subsplit[1]);
    		      else if(subsplit[0].equals("W"))
    		    	  setWifi(subsplit[1]);
    		      else if(subsplit[0].equals("P"))
    		    	  processProfile(subsplit[1]);
    		}
    		return 1;
    	}
    	return 0;
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
