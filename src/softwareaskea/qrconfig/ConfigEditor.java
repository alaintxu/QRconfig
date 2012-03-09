package softwareaskea.qrconfig;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ConfigEditor {
	private QRConfigActivity	qrca;
	private BluetoothAdapter	mBluetoothAdapter;
	private WifiManager			mWifiManager;
	
	public ConfigEditor(){}
	
	public ConfigEditor(QRConfigActivity qrca){
		this.qrca			=	qrca;
		mBluetoothAdapter	=	BluetoothAdapter.getDefaultAdapter();
		mWifiManager		=	(WifiManager) qrca.getSystemService(Context.WIFI_SERVICE);
	}
	
    public boolean bluetoothAldatu (String aktibatu){
    	if (aktibatu.equals("On") && !mBluetoothAdapter.isEnabled()){
    		Toast.makeText(qrca.getApplicationContext(), R.string.blueOn, Toast.LENGTH_LONG);
    		return mBluetoothAdapter.enable();
    	}else if(aktibatu.equals("Off") && mBluetoothAdapter.isEnabled()){
    		Toast.makeText(qrca.getApplicationContext(), R.string.blueOff, Toast.LENGTH_LONG);
    		return mBluetoothAdapter.disable();
    	}
    	return false;
    }
    
    public boolean wifiAldatu (String aktibatu){
    	if(aktibatu.equals("On") && !mWifiManager.isWifiEnabled()){  
    	    mWifiManager.setWifiEnabled(true);
    	    Toast.makeText(qrca.getApplicationContext(), R.string.wifiOn, Toast.LENGTH_LONG);
    	}else if(aktibatu.equals("Off") && mWifiManager.isWifiEnabled()){  
    	    mWifiManager.setWifiEnabled(false);
    	    Toast.makeText(qrca.getApplicationContext(), R.string.wifiOff, Toast.LENGTH_LONG);
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
    		Toast.makeText(qrca.getApplicationContext(), R.string.wrongQr, Toast.LENGTH_LONG);
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
}
