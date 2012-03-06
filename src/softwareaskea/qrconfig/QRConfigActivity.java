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
        
        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(0,ActionBar.DISPLAY_SHOW_TITLE);
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
        case android.R.id.home:
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, QRConfigActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
            case R.id.scanQr:
                scanQrCode();
                return true;
            case R.id.moreOptions:
            	//Settings
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
    
    public void quit(){
    	this.finish();
    }

	public ButtonListener getButtonListener() {
		// TODO Auto-generated method stub
		return buttonListener;
	}

}