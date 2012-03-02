package softwareaskea.qrconfig;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.net.wifi.WifiManager;

public class QRConfigActivity extends Activity {

    private OnClickListener buttonListener = new OnClickListener() {
        public void onClick(View v) {
        	int id			=	v.getId();
        	int blueOnId	=	R.id.blueOn;
        	int blueOffId	=	R.id.blueOff;
        	int wifiOnId	=	R.id.wifiOn;
        	int wifiOffId	=	R.id.wifiOff;
        	int scanQr		=	R.id.scanQr;
        	if(id==blueOnId)
        		bluetoothAldatu("On");
        	else if(id==blueOffId)
        		bluetoothAldatu("Off");
        	else if(id==wifiOnId)
        		wifiAldatu("On");
        	else if(id==wifiOffId)
        		wifiAldatu("Off");
        	else if(id==scanQr)
        		scanQrCode();
        }
    };
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Capture our button from layout
        Button blueOn = (Button)findViewById(R.id.blueOn);
        Button blueOff = (Button)findViewById(R.id.blueOff);
        Button wifiOn = (Button)findViewById(R.id.wifiOn);
        Button wifiOff = (Button)findViewById(R.id.wifiOff);
        Button scanQr = (Button)findViewById(R.id.scanQr);
        // Register the onClick listener with the implementation above
        blueOn.setOnClickListener(buttonListener);
        blueOff.setOnClickListener(buttonListener);
        wifiOn.setOnClickListener(buttonListener);
        wifiOff.setOnClickListener(buttonListener);
        scanQr.setOnClickListener(buttonListener);

    }
    
    //ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabs, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scanQr:
                scanQrCode();
                return true;
            case R.id.moreOptions:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private boolean bluetoothAldatu (String aktibatu){
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (aktibatu.equals("On") && !mBluetoothAdapter.isEnabled()){
    		notify(R.string.blueOn,Toast.LENGTH_LONG);
    		return mBluetoothAdapter.enable();
    	}else if(aktibatu.equals("Off") && mBluetoothAdapter.isEnabled()){
    	    notify(R.string.blueOff,Toast.LENGTH_LONG);
    		return mBluetoothAdapter.disable();
    	}
    	return false;
    }
    
    private boolean wifiAldatu (String aktibatu){
    	WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);  
    	if(aktibatu.equals("On") && !wifiManager.isWifiEnabled()){  
    	    wifiManager.setWifiEnabled(true);
    	    notify(R.string.wifiOn,Toast.LENGTH_LONG);
    	}else if(aktibatu.equals("Off") && wifiManager.isWifiEnabled()){  
    	    wifiManager.setWifiEnabled(false);
    	    notify(R.string.wifiOff,Toast.LENGTH_LONG);
    	}
    	return false;
    }
    private boolean scanQrCode(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);

    	return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                if(format.equals("QR_CODE"))
                	processQr(contents);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
    
    private void processQr(String qr){
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
    		notify(R.string.wrongQr,Toast.LENGTH_LONG);
    	}
    }
    
    private void notify(CharSequence text,int duration){
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    private void notify(int rid,int duration){
    	Resources res = getResources();
    	String text = String.format(res.getString(rid));
    	
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }

}