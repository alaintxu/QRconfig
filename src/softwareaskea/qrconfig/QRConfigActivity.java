package softwareaskea.qrconfig;


import java.util.List;
import java.util.Vector;

import softwareaskea.qrconfig.db.*;
import softwareaskea.qrconfig.fragment.*;
import softwareaskea.qrconfig.listener.*;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class QRConfigActivity extends FragmentActivity {
	private ConfigEditor	configEditor;
	private ButtonListener	buttonListener;
	private DatabaseManager	databaseManager;
	private PagerAdapter	mPagerAdapter;
	private ViewPager		pager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.configEditor	=	new ConfigEditor(this);
        this.buttonListener	=	new ButtonListener(this);
        this.databaseManager=	new	DatabaseManager();
        setContentView(R.layout.main);
        
        this.initialisePaging();
    }
    
    private void initialisePaging() {
    	List<Fragment>	fragments	=	new	Vector<Fragment>();
    	fragments.add(Fragment.instantiate(this, ManualFragment.class.getName()));
    	fragments.add(Fragment.instantiate(this, HomeFragment.class.getName()));
    	fragments.add(Fragment.instantiate(this, PresetsFragment.class.getName()));
    	
    	mPagerAdapter	=	new	QRConfigPagerAdapter(super.getSupportFragmentManager(),fragments,getResources().getStringArray(R.array.page_adapter_pages));
    	
    	pager	=	(ViewPager)super.findViewById(R.id.viewpager);
    	
    	pager.setAdapter(this.mPagerAdapter);
    	pager.setCurrentItem(1);
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
        return super.onCreateOptionsMenu(menu);
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

	public void setFragment(int item) {
		pager.setCurrentItem(item, true);
	}

}