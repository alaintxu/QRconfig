package softwareaskea.qrconfig.listener;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.R;
import android.view.View;
import android.widget.Switch;

public class ButtonListener implements android.view.View.OnClickListener{

	private ConfigEditor		configEditor;
	
	public ButtonListener(){}
	
	public ButtonListener(ConfigEditor configEditor){
		this.configEditor	=	configEditor;
	}

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
	    	case R.id.scanQr:
	    	case R.id.homeScanQr:
	    		configEditor.scanQrCode();
	    		break;
	    	default:
	    		break;
    	}
    }
    
	/************  Actions  ************/

    private void saveAsProfileAction() {
		//Create profile from existing configuration an save in the DB
    	configEditor.saveManualAsProfile();
	}

	private void wifiAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setWifi("1");
    	else
    		configEditor.setWifi("0");
    }
    
    private void btAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setBT("1");
    	else
    		configEditor.setBT("0");
    }
    
    private void vbAction(Switch switchv){
		Boolean isChecked	=	switchv.isChecked();
		
    	if(isChecked)
    		configEditor.setVB("1");
    	else
    		configEditor.setVB("0");
    }
}
