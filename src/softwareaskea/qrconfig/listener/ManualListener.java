package softwareaskea.qrconfig.listener;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.InterfaceFunctions;
import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.db.ProfileDAO;
import softwareaskea.qrconfig.profiles.Profile;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class ManualListener implements OnClickListener, OnSeekBarChangeListener {
	private ConfigEditor	mConfigEditor	=	null;	
	private Activity 		mActivity		=	null;
	
	public int	ACTION_MUTE			=	1;
	public int	ACTION_MAX_VOLUME	=	2;	
	
	public ManualListener(){};
	public ManualListener(Activity activity){
		setActivity(activity);
	}
	
	public void setActivity(Activity activity){
		mActivity		=	activity;
		mConfigEditor	=	new ConfigEditor(activity);
	}
	
	/************  Overrides  ************/
	@Override
	public void onClick(View v) {
    	/** Identify the clicked widget
    	and perform the corresponding action**/
    	int id			=	v.getId();
    	
    	switch(id){
        	/***** Switches *****/
    		//Wifi Switch
	    	case R.id.wifiSwitch:
	    		wifiAction((Switch)v,R.id.wifiCheckBox);
	        	break;
	    	//Bluetooth Switch
	    	case R.id.btSwitch:
	    		btAction((Switch)v,R.id.btCheckBox);
	        	break;
		    //GPS Switch
	    	/*case R.id.gpsSwitch:
	    		gpsAction((Switch)v,R.id.gpsCheckBox);
	        	break;*/
	        //Vibration Switch
	    	case R.id.vbSwitch:
	    		vbAction((Switch)v,R.id.vbCheckBox);
	        	break;
	        /***** Buttons *****/
 	        //Save Button
	    	case R.id.saveAsProfile:
	    		saveAsProfileAction();
	    		break;
	    	//Max Volume Button
	    	case R.id.volMaxBtn:
	    		volAction(ACTION_MAX_VOLUME);
	    		break;
	    	//Mute Button
	    	case R.id.volMuteBtn:
	    		volAction(ACTION_MUTE);
	    		break;
	    	default:
	    		break;
    	}
    }
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int vol	=	seekBar.getProgress();
		int checkBoxId	=	-1;
		if(seekBar.getId()==R.id.rtSeekBar){
			mConfigEditor.setRTVolume(vol);
			checkBoxId	=	R.id.rtCheckBox;
		}else if(seekBar.getId()==R.id.mSeekBar){
			mConfigEditor.setMVolume(vol);
			checkBoxId	=	R.id.mCheckBox;
		}else if(seekBar.getId()==R.id.alSeekBar){
			mConfigEditor.setAlVolume(vol);
			checkBoxId	=	R.id.alCheckBox;
		}
		
		
		if(checkBoxId!=-1)
			((CheckBox)mActivity.findViewById(checkBoxId)).setChecked(true);
	}
	
	/************  Actions  ************/
    /**
     * Create profile from existing configuration and save
     * in the DB, asking the profile name in an alert
     */
    private void saveAsProfileAction() {
    	AlertDialog.Builder	alert	=	new AlertDialog.Builder(mActivity);
    	Resources		res			=	mActivity.getResources();
    	String			title		=	res.getString(R.string.profile_name);
    	String			profileName	=	res.getString(R.string.enter_profile_name);
    	String			okBtn		=	res.getString(R.string.save);
    	String			cancelBtn	=	res.getString(R.string.cancel);
    	
    	alert.setTitle(title);  
    	alert.setMessage(profileName);                

    	// Set an EditText view to get user input   
    	final EditText input = new EditText(mActivity); 
    	alert.setView(input);

    	//Ok button
    	alert.setPositiveButton(okBtn, 
    			new DialogInterface.OnClickListener(){  
    	    		public void onClick(DialogInterface dialog, int whichButton) {  
    	    			String value = input.getText().toString();
    	    			Profile profile	=	manual2profile(value);
    	    			ProfileDAO profileDAO	=	new ProfileDAO(mActivity);
    	    			profileDAO.createProfile(profile);
    	    			profileDAO.close();
    	    			return;                  
    	    		}  
    	     	}
    	);  

    	alert.setNegativeButton(cancelBtn,
    			new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int which) {
    	        		return;   
    	        	}
    	    	}
    	);
    	alert.show();

	}
    /**
     * Change wifi options and interface state
     * @param switchv changed switch
     * @param checkBoxId checkbox of the option
     */
	private void wifiAction(Switch switchv,int checkBoxId){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		mConfigEditor.setWifi(Profile.WIFI_ON);
    	else
    		mConfigEditor.setWifi(Profile.WIFI_OFF);
    	
		((CheckBox)mActivity.findViewById(checkBoxId)).setChecked(true);
    }
    
	/**
     * Change bluetooth options and interface state
     * @param switchv changed switch
     * @param checkBoxId checkbox of the option
     */
    private void btAction(Switch switchv,int checkBoxId){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		mConfigEditor.setBT(Profile.BLUETOOTH_ON);
    	else
    		mConfigEditor.setBT(Profile.BLUETOOTH_OFF);
    	
		((CheckBox)mActivity.findViewById(checkBoxId)).setChecked(true);
    }
    
    /**
     * Change vibration options and interface state
     * @param switchv changed switch
     * @param checkBoxId checkbox of the option
     */
    private void vbAction(Switch switchv,int checkBoxId){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		mConfigEditor.setVB(Profile.VIBRATION_ON);
    	else
    		mConfigEditor.setVB(Profile.VIBRATION_OFF);
    	
		((CheckBox)mActivity.findViewById(checkBoxId)).setChecked(true);
    }
    
    /**
     * Change volumen options depending on the action
     * @param action type of action to perform
     */
	private void volAction(int action) {
		Boolean		somethingChanged	=	false;
		CheckBox	mCheckBox	=	(CheckBox)mActivity.findViewById(R.id.mCheckBox);
		CheckBox	rtCheckBox	=	(CheckBox)mActivity.findViewById(R.id.rtCheckBox);
		CheckBox	alCheckBox	=	(CheckBox)mActivity.findViewById(R.id.alCheckBox);
		if(action==ACTION_MAX_VOLUME){
			if(mCheckBox.isChecked()){
				mConfigEditor.setMVolume(mConfigEditor.getMMaxVolume());
				somethingChanged=true;
			}
			if(rtCheckBox.isChecked()){
				mConfigEditor.setRTVolume(mConfigEditor.getRTMaxVolume());
				somethingChanged=true;
			}
			if(alCheckBox.isChecked()){
				mConfigEditor.setAlVolume(mConfigEditor.getAlMaxVolume());
				somethingChanged=true;
			}
		}else if(action==ACTION_MUTE){
			if(mCheckBox.isChecked()){
				mConfigEditor.setMVolume(0);
				somethingChanged=true;
			}
			if(rtCheckBox.isChecked()){
				mConfigEditor.setRTVolume(0);
				somethingChanged=true;
			}
			if(alCheckBox.isChecked()){
				mConfigEditor.setAlVolume(0);
				somethingChanged=true;
			}
		}
		
		if(somethingChanged)	updateManualViewStatus();
	}

	/**
	 * Action to create a profile with current configuration
	 * @param name Name of the profile
	 * @return a profile with the current configuration
	 */
	private Profile manual2profile(String name){		
		int	wifi		=	InterfaceFunctions.getSwitchValue(
				mActivity,
				R.id.wifiCheckBox,
				R.id.wifiSwitch,
				Profile.WIFI_NOT_SET,
				Profile.WIFI_OFF,
				Profile.WIFI_ON
		);
		int	bluetooth	=	InterfaceFunctions.getSwitchValue(
				mActivity,
				R.id.btCheckBox,
				R.id.btSwitch,
				Profile.BLUETOOTH_NOT_SET,
				Profile.BLUETOOTH_OFF,
				Profile.BLUETOOTH_ON
		);
		int	vibration	=	InterfaceFunctions.getSwitchValue(
				mActivity,
				R.id.vbCheckBox,
				R.id.vbSwitch,
				Profile.VIBRATION_NOT_SET,
				Profile.VIBRATION_OFF,
				Profile.VIBRATION_ON
		);
		int	multimedia	=	InterfaceFunctions.getSeekBarValue(
				mActivity,
				R.id.mCheckBox,
				R.id.mSeekBar,
				Profile.WIFI_NOT_SET
		);
		int	ringtone	=	InterfaceFunctions.getSeekBarValue(
				mActivity,
				R.id.rtCheckBox,
				R.id.rtSeekBar,
				Profile.WIFI_NOT_SET
		);
		int	alarm		=	InterfaceFunctions.getSeekBarValue(
				mActivity,
				R.id.alCheckBox,
				R.id.alSeekBar,
				Profile.WIFI_NOT_SET
		);
		
		Profile profile	=	new Profile(
				name,
				wifi,
				bluetooth,
				vibration,
				multimedia,
				ringtone,
				alarm
		);
		
		return profile;
	}
    
    public void updateManualViewStatus(){

    	Boolean	isBTEnabled		=	mConfigEditor.isBTEnabled();
    	Boolean isWifiEnabled	=	mConfigEditor.isWifiEnabled();
    	Boolean isVBEnabled		=	mConfigEditor.isVBEnabled();
    	int rtVol				=	mConfigEditor.getRTVolume();
    	int mVol				=	mConfigEditor.getMVolume();
    	int alVol				=	mConfigEditor.getAlVolume();
    	
    	Switch btSwitch			=	(Switch) mActivity.findViewById(R.id.btSwitch);
    	Switch wifiSwitch		=	(Switch) mActivity.findViewById(R.id.wifiSwitch);
    	Switch vbSwitch			=	(Switch) mActivity.findViewById(R.id.vbSwitch);
    	SeekBar	rtSeekBar		=	(SeekBar) mActivity.findViewById(R.id.rtSeekBar);
    	SeekBar	mSeekBar		=	(SeekBar) mActivity.findViewById(R.id.mSeekBar);
    	SeekBar	alSeekBar		=	(SeekBar) mActivity.findViewById(R.id.alSeekBar);
    	try{
	        btSwitch.setChecked(isBTEnabled);
	        wifiSwitch.setChecked(isWifiEnabled);
	        vbSwitch.setChecked(isVBEnabled);
	        rtSeekBar.setProgress(rtVol);
	        mSeekBar.setProgress(mVol);
	        alSeekBar.setProgress(alVol);
	    }catch(Exception e){
	    	Toast.makeText(mActivity.getApplicationContext(), R.string.error_update_view_status, Toast.LENGTH_LONG).show();
	    }
    	
    }
}
