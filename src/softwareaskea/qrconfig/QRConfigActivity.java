package softwareaskea.qrconfig;


import java.util.List;
import java.util.Vector;

import softwareaskea.qrconfig.db.ProfileDAO;
import softwareaskea.qrconfig.fragment.*;
import softwareaskea.qrconfig.profiles.Profile;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class QRConfigActivity extends FragmentActivity {
	private ConfigEditor	mConfigEditor;
	private PagerAdapter	mPagerAdapter;
	private ViewPager		mPager;
	private ProfileDAO		mProfileDAO;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mConfigEditor	=	new ConfigEditor(this);
        mProfileDAO		=	new ProfileDAO(this);
        setContentView(R.layout.main);
        
        this.initialisePaging();
    }
    
    private void initialisePaging() {
    	List<Fragment>	fragments	=	new	Vector<Fragment>();
    	fragments.add(Fragment.instantiate(this, ManualFragment.class.getName()));
    	fragments.add(Fragment.instantiate(this, ProfileListFragment.class.getName()));
    	
    	mPagerAdapter	=	new	QRConfigPagerAdapter(super.getSupportFragmentManager(),fragments,getResources().getStringArray(R.array.page_adapter_pages));
    	
    	mPager	=	(ViewPager)super.findViewById(R.id.viewpager);
    	
    	mPager.setAdapter(this.mPagerAdapter);
    	mPager.setCurrentItem(1);
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
                scanQrCode();
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
    	super.onResume();
    }

	@Override
	protected void onPause() {
		mProfileDAO.close();
		super.onPause();
	}
    
	/*********************  QR Code  *********************/
	public void scanQrCode(){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        this.startActivityForResult(intent, 0);
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
                if(format.equals("QR_CODE")){
                	Profile profile	=	QRFunctions.getQRProfile(contents, mProfileDAO);
                	mConfigEditor.applyProfile(profile);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.scan_qr_canceled, Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /*********************  Profile  *********************/
    
	/**
	 * Loads profile from database and sets current status to match profile
	 * @param profileName	name of the profile to load
	 * @return returns if there was an error (-1) or not (1)
	 */
    public int processProfile(String profileName) {
    	try{
    		Profile profile	=	mProfileDAO.loadProfile(profileName);
    		mConfigEditor.applyProfile(profile);
    		return 1;
    	}catch(Exception e){
    		return -1;
    	}
	}
    
    public void quit(){
    	this.finish();
    }
    
    /**************  Getters  **************/
    public ProfileDAO getProfileDAO(){
    	return mProfileDAO;
    }

	public void setFragment(int item) {
		mPager.setCurrentItem(item, true);
	}

}