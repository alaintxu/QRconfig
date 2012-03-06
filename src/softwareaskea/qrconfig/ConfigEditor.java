package softwareaskea.qrconfig;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class ConfigEditor {
	private QRConfigActivity qrca;
	
	public ConfigEditor(){}
	
	public ConfigEditor(QRConfigActivity qrca){
		this.qrca	=	qrca;
	}
	
    public boolean bluetoothAldatu (String aktibatu){
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (aktibatu.equals("On") && !mBluetoothAdapter.isEnabled()){
    		qrca.notify(R.string.blueOn,Toast.LENGTH_LONG);
    		return mBluetoothAdapter.enable();
    	}else if(aktibatu.equals("Off") && mBluetoothAdapter.isEnabled()){
    		qrca.notify(R.string.blueOff,Toast.LENGTH_LONG);
    		return mBluetoothAdapter.disable();
    	}
    	return false;
    }
    
    public boolean wifiAldatu (String aktibatu){
    	WifiManager wifiManager = (WifiManager) qrca.getSystemService(Context.WIFI_SERVICE);  
    	if(aktibatu.equals("On") && !wifiManager.isWifiEnabled()){  
    	    wifiManager.setWifiEnabled(true);
    	    qrca.notify(R.string.wifiOn,Toast.LENGTH_LONG);
    	}else if(aktibatu.equals("Off") && wifiManager.isWifiEnabled()){  
    	    wifiManager.setWifiEnabled(false);
    	    qrca.notify(R.string.wifiOff,Toast.LENGTH_LONG);
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
    		qrca.notify(R.string.wrongQr,Toast.LENGTH_LONG);
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

}
