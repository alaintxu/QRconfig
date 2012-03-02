package softwareaskea.qrconfig;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class QRConfigActivity extends Activity {
	private ConfigEditor	configEditor	=	null;
	private ButtonListener	buttonListener	=	null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.configEditor	=	new ConfigEditor(this);
        this.buttonListener	=	new ButtonListener(this);
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
        
        // setup action bar for tabs
        /*ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                .setText(R.string.home)
                .setTabListener(new TabListener<HomeFragment>(
                        this, "home", HomeFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
            .setText(R.string.presets)
            .setTabListener(new TabListener<PresetsFragment>(
                    this, "presets", PresetsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
            .setText(R.string.presets)
            .setTabListener(new TabListener<ManualFragment>(
                    this, "manual", ManualFragment.class));
        actionBar.addTab(tab);*/
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
    
    public boolean scanQrCode(){
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
                	configEditor.processQr(contents);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
    
    public ConfigEditor getConfigEditor(){
    	return this.configEditor;
    }
    
    public void notify(CharSequence text,int duration){
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
    public void notify(int rid,int duration){
    	Resources res = getResources();
    	String text = String.format(res.getString(rid));
    	
    	Context context = getApplicationContext();
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }

}