package softwareaskea.qrconfig;


import java.util.List;
import java.util.Vector;

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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class QRConfigActivity extends FragmentActivity {
	private ConfigEditor	configEditor;
	private ButtonListener	buttonListener;
	private PagerAdapter	mPagerAdapter;
	private ViewPager		pager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        configEditor	=	new ConfigEditor(this);
        buttonListener	=	new ButtonListener(configEditor);
        setContentView(R.layout.main);
        
        this.initialisePaging();
    }
    
    private void initialisePaging() {
    	List<Fragment>	fragments	=	new	Vector<Fragment>();
    	fragments.add(Fragment.instantiate(this, ManualFragment.class.getName()));
    	fragments.add(Fragment.instantiate(this, HomeFragment.class.getName()));
    	fragments.add(Fragment.instantiate(this, ProfileListFragment.class.getName()));
    	
    	mPagerAdapter	=	new	QRConfigPagerAdapter(super.getSupportFragmentManager(),fragments,getResources().getStringArray(R.array.page_adapter_pages));
    	
    	pager	=	(ViewPager)super.findViewById(R.id.viewpager);
    	
    	pager.setAdapter(this.mPagerAdapter);
    	pager.setCurrentItem(1);
	}

	@Override
    protected void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
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
                configEditor.scanQrCode();
                return true;
            case R.id.exit:
            	quit();
                return true;
            case R.id.addProfile:
            	Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT);	
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onResume(){
    	configEditor.resume();
    	super.onResume();
    }

	@Override
	protected void onPause() {
		configEditor.pause();
		super.onPause();
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
    
    public void updateViewStatus(){
    	Boolean	isBTEnabled		=	configEditor.isBTEnabled();
    	Boolean isWifiEnabled	=	configEditor.isWifiEnabled();
    	Boolean isVBEnabled		=	configEditor.isWifiEnabled();
    	int rtVol				=	configEditor.getRTVolume();
    	int nVol				=	configEditor.getNVolume();
    	int mVol				=	configEditor.getMVolume();
    	
    	Switch btSwitch			=	(Switch) findViewById(R.id.btSwitch);
    	Switch wifiSwitch		=	(Switch) findViewById(R.id.wifiSwitch);
    	Switch vbSwitch			=	(Switch) findViewById(R.id.vbSwitch);
    	SeekBar	rtSeekBar		=	(SeekBar) findViewById(R.id.rtSeekBar);
    	SeekBar	nSeekBar		=	(SeekBar) findViewById(R.id.nSeekBar);
    	SeekBar	mSeekBar		=	(SeekBar) findViewById(R.id.mSeekBar);
    	
        btSwitch.setChecked(isBTEnabled);
        wifiSwitch.setChecked(isWifiEnabled);
        vbSwitch.setChecked(isVBEnabled);
        rtSeekBar.setProgress(rtVol);
        nSeekBar.setProgress(nVol);
        mSeekBar.setProgress(mVol);
    }

	
	public void updateProfileList(){
    	try{
        	/*List<Profile>	profiles	=	configEditor.getProfileList();
        	
        	ArrayAdapter<Profile>	adapter		=	new ArrayAdapter<Profile>(getApplicationContext(), 0, profiles);
        	
        	ListView		lv			=	(ListView) findViewById(R.id.profileList);
        	
    		lv.setAdapter(adapter);*/
    	}catch(Exception e){
    		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    	}
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