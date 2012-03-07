package softwareaskea.qrconfig;


import softwareaskea.qrconfig.db.*;
import softwareaskea.qrconfig.fragment.*;
import softwareaskea.qrconfig.listener.*;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class QRConfigActivity extends Activity {
	private ConfigEditor	configEditor	=	null;
	private ButtonListener	buttonListener	=	null;
	private DatabaseManager	databaseManager	=	null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.configEditor	=	new ConfigEditor(this);
        this.buttonListener	=	new ButtonListener(this);
        this.databaseManager=	new	DatabaseManager();
        setContentView(R.layout.main);
        
        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        TabListener<HomeFragment>		homeTabListener = new TabListener<HomeFragment>(this,"home",HomeFragment.class);
        TabListener<PresetsFragment>	presetsTabListener = new TabListener<PresetsFragment>(this,"presets",PresetsFragment.class);
        TabListener<ManualFragment>		manualTabListener = new TabListener<ManualFragment>(this,"home",ManualFragment.class);
        
        Tab tab = actionBar.newTab()
                .setText(R.string.home)
                .setTabListener(homeTabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab()
            .setText(R.string.presets)
            .setTabListener(presetsTabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab()
            .setText(R.string.manual)
            .setTabListener(manualTabListener);
        actionBar.addTab(tab);
        
        if(savedInstanceState != null){
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab",0));
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	outState.putInt("tab",getActionBar().getSelectedNavigationIndex());
    }
    
    //Overflow menu in Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }
    
    //Overflow menu item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scanQr:
                scanQrCode();
                return true;
            case R.id.exit:
            	quit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Call Barcode Scanner Intent to manage QR codes
     */
    public void scanQrCode(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }
    
    /**
     * Manage result of QR code scan
     * @param requestCode	0 if there is any result
     * @param resultCode	the type of result
     * @param intent	Barcode Scanner Intent
     */
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
    
    public void quit(){
    	this.finish();
    }
    
    /**************  Getters  **************/
    public ConfigEditor getConfigEditor(){
    	return this.configEditor;
    }

	public ButtonListener getButtonListener() {
		return buttonListener;
	}

}