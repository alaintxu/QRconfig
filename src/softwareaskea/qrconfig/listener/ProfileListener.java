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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ProfileListener implements OnClickListener, OnSeekBarChangeListener{
	private Activity mActivity;
	
	private int	ACTION_MUTE			=	1;
	private int	ACTION_MAX_VOLUME	=	2;
	
	public ProfileListener(){}
	
	public ProfileListener(Activity activity){
		setActivity(activity);
	}
	
	public void setActivity(Activity activity){
		mActivity	=	activity;
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
	    	case R.id.wifiSwitchProf:
	    		this.activateCheckBox(R.id.wifiCheckBoxProf);
	        	break;
	    	//Bluetooth Switch
	    	case R.id.btSwitchProf:
	    		this.activateCheckBox(R.id.btCheckBoxProf);
	        	break;
	        //Vibration Switch
	    	case R.id.vbSwitchProf:
	    		this.activateCheckBox(R.id.vbCheckBoxProf);
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
		int checkBoxId	=	-1;
		if(seekBar.getId()==R.id.rtSeekBarProf)
			checkBoxId	=	R.id.rtCheckBoxProf;
		else if(seekBar.getId()==R.id.mSeekBarProf)
			checkBoxId	=	R.id.mCheckBoxProf;
		else if(seekBar.getId()==R.id.alSeekBarProf)
			checkBoxId	=	R.id.alCheckBoxProf;
		
		
		
		if(checkBoxId!=-1)
			this.activateCheckBox(checkBoxId);
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
    	    			Profile profile	=	form2profile(value);
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
    
    private void activateCheckBox(int checkBoxId){
		((CheckBox)mActivity.findViewById(checkBoxId)).setChecked(true);
    }
    
    /**
     * Change volumen options depending on the action
     * @param action type of action to perform
     */
	private void volAction(int action) {
		ConfigEditor	mConfigEditor	=	new ConfigEditor(mActivity);

		CheckBox	mCheckBox	=	(CheckBox)mActivity.findViewById(R.id.mCheckBoxProf);
		CheckBox	rtCheckBox	=	(CheckBox)mActivity.findViewById(R.id.rtCheckBoxProf);
		CheckBox	alCheckBox	=	(CheckBox)mActivity.findViewById(R.id.alCheckBoxProf);
		
		SeekBar		mSeekBar	=	(SeekBar)mActivity.findViewById(R.id.mSeekBarProf);
		SeekBar		rtSeekBar	=	(SeekBar)mActivity.findViewById(R.id.rtSeekBarProf);
		SeekBar		alSeekBar	=	(SeekBar)mActivity.findViewById(R.id.alSeekBarProf);
		
		if(action==ACTION_MAX_VOLUME){
			if(mCheckBox.isChecked())
				mSeekBar.setProgress(mConfigEditor.getMMaxVolume());
			if(rtCheckBox.isChecked())
				rtSeekBar.setProgress(mConfigEditor.getRTMaxVolume());
			if(alCheckBox.isChecked())
				alSeekBar.setProgress(mConfigEditor.getAlMaxVolume());
		}else if(action==ACTION_MUTE){
			if(mCheckBox.isChecked())
				mSeekBar.setProgress(0);
			if(rtCheckBox.isChecked())
				rtSeekBar.setProgress(0);
			if(alCheckBox.isChecked())
				alSeekBar.setProgress(0);
		}
	}
	/**
	 * Action to create a profile with current configuration
	 * @param name Name of the profile
	 * @return a profile with the current configuration
	 */
	private Profile form2profile(String name){		
		int	wifi		=	InterfaceFunctions.getSwitchValue(
				mActivity,
				R.id.wifiCheckBoxProf,
				R.id.wifiSwitchProf,
				Profile.WIFI_NOT_SET,
				Profile.WIFI_OFF,
				Profile.WIFI_ON
		);
		int	bluetooth	=	InterfaceFunctions.getSwitchValue(
				mActivity,
				R.id.btCheckBoxProf,
				R.id.btSwitchProf,
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
				R.id.mCheckBoxProf,
				R.id.mSeekBarProf,
				Profile.WIFI_NOT_SET
		);
		int	ringtone	=	InterfaceFunctions.getSeekBarValue(
				mActivity,
				R.id.rtCheckBoxProf,
				R.id.rtSeekBarProf,
				Profile.WIFI_NOT_SET
		);
		int	alarm		=	InterfaceFunctions.getSeekBarValue(
				mActivity,
				R.id.alCheckBoxProf,
				R.id.alSeekBarProf,
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
}
