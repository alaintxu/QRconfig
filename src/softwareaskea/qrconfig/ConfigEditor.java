package softwareaskea.qrconfig;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ConfigEditor {
	private BluetoothAdapter	mBluetoothAdapter;
	private WifiManager			mWifiManager;
	private Context				context;
	private Activity			activity;
	
	public ConfigEditor(){}
	
	public ConfigEditor(Activity activity){
		mBluetoothAdapter	=	BluetoothAdapter.getDefaultAdapter();
		mWifiManager		=	(WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		context				=	activity.getApplicationContext();
		this.activity		=	activity;
	}
	
    public boolean bluetoothAldatu (String aktibatu){
    	if (aktibatu.equals("1") && !mBluetoothAdapter.isEnabled()){
    		Toast.makeText(context, R.string.blueOn, Toast.LENGTH_LONG);
    		return mBluetoothAdapter.enable();
    	}else if(aktibatu.equals("0") && mBluetoothAdapter.isEnabled()){
    		Toast.makeText(context, R.string.blueOff, Toast.LENGTH_LONG);
    		return mBluetoothAdapter.disable();
    	}
    	return false;
    }
    
    public boolean wifiAldatu (String aktibatu){
    	if(aktibatu.equals("1") && !mWifiManager.isWifiEnabled()){  
    	    mWifiManager.setWifiEnabled(true);
    	    Toast.makeText(context, R.string.wifiOn, Toast.LENGTH_LONG);
    	}else if(aktibatu.equals("0") && mWifiManager.isWifiEnabled()){  
    	    mWifiManager.setWifiEnabled(false);
    	    Toast.makeText(context, R.string.wifiOff, Toast.LENGTH_LONG);
    	}
    	return false;
    }
    
    public void processQr(String qr){
    	String pattern	=	"MCONFIG:(\\S+:\\S+;)*;";
    	if(qr.matches(pattern)){
    		qr = qr.replace("MCONFIG:","");
    		qr = qr.replace(";;","");
    		String[] split = qr.split(";");
    		for (String s : split) {
    		      String[] subsplit = s.split(":");
    		      if(subsplit[0].equals("B"))
    		    	  bluetoothAldatu(subsplit[1]);
    		      else if(subsplit[0].equals("W"))
    		    	  wifiAldatu(subsplit[1]);
    		}
    	}else{
    		Toast.makeText(context, R.string.wrongQr, Toast.LENGTH_LONG);
    	}
    }
    
    public static String getGoogleChartQRLink(int width, int height, String data){
    	String	link	=	"https://chart.googleapis.com/chart?chs="+width+"x"+height+"&cht=qr&chl="+data;
    	return link;
    }
    public static String getGoogleChartQRLink(String data){
    	String	link	=	getGoogleChartQRLink(256,256,data);
    	return link;
    }

    public Boolean isWifiEnabled(){
    	return mWifiManager.isWifiEnabled();
    }
    
    public Boolean isBluetoothEnabled(){
    	return mBluetoothAdapter.isEnabled();
    }
    

    
    /**
     * Call Barcode Scanner Intent to manage QR codes
     */
    public void scanQrCode(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        activity.startActivityForResult(intent, 0);
    }
    
    /**
     * Manage result of QR code scan
     * @param requestCode	0 if there is any result
     * @param resultCode	the type of result
     * @param intent	Barcode Scanner Intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == activity.RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                if(format.equals("QR_CODE"))
                	processQr(contents);
            } else if (resultCode == activity.RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
