package softwareaskea.qrconfig.listener;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.profiles.Profile;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;

public class ButtonListener implements android.view.View.OnClickListener, OnSeekBarChangeListener{
	public static int	ACTION_MUTE			=	1;
	public static int	ACTION_MAX_VOLUME	=	2;
	
	private ConfigEditor		configEditor;
	private Activity			mActivity;
	private Resources			res;
	
	public ButtonListener(){}
	
	public ButtonListener(Activity activity, ConfigEditor configEditor){
		this.configEditor	=	configEditor;
		this.mActivity		=	activity;
		this.res			=	activity.getResources();
	}

    @Override
	public void onClick(View v) {
    	int id			=	v.getId();
    	
    	switch(id){
	    	case R.id.wifiSwitch:
	    		wifiAction((Switch)v);
	        	break;
	    	case R.id.btSwitch:
	    		btAction((Switch)v);
	        	break;
	    	case R.id.vbSwitch:
	    		vbAction((Switch)v);
	        	break;
	    	case R.id.saveAsProfile:
	    		saveAsProfileAction();
	    		break;
	    	case R.id.volMaxBtn:
	    		volAction(ACTION_MAX_VOLUME);
	    		break;
	    	case R.id.volMuteBtn:
	    		volAction(ACTION_MUTE);
	    		break;
	    	case R.id.scanQr:
	    	case R.id.homeScanQr:
	    		configEditor.scanQrCode();
	    		break;
	    	default:
	    		break;
    	}
    }

	/************  Actions  ************/
    /**
     * Create profile from existing configuration and save
     * in the DB, asking the profile name in an alert
     */
    private void saveAsProfileAction() {
    	AlertDialog.Builder	alert	=	new AlertDialog.Builder(mActivity);
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
    	    			configEditor.saveManualAsProfile(value);
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

	private void wifiAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setWifi(Profile.WIFI_ON);
    	else
    		configEditor.setWifi(Profile.WIFI_OFF);
    }
    
    private void btAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setBT(Profile.BLUETOOTH_ON);
    	else
    		configEditor.setBT(Profile.BLUETOOTH_OFF);
    }
    
    private void vbAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setVB(Profile.VIBRATION_ON);
    	else
    		configEditor.setVB(Profile.VIBRATION_OFF);
    }
    
	private void volAction(int action) {
		if(action==ACTION_MAX_VOLUME){
			configEditor.setRTVolume(configEditor.getRTMaxVolume());
			configEditor.setMVolume(configEditor.getMMaxVolume());
		}else if(action==ACTION_MUTE){
			configEditor.setRTVolume(0);
			configEditor.setMVolume(0);
		}else{
			return;
		}
		configEditor.updateViewStatus();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int vol	=	seekBar.getProgress();
		if(seekBar.getId()==R.id.rtSeekBar)
			configEditor.setRTVolume(vol);
		else if(seekBar.getId()==R.id.mSeekBar)
			configEditor.setMVolume(vol);
	}
}
