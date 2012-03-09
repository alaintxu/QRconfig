package softwareaskea.qrconfig.listener;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.R;
import android.view.View;

public class ButtonListener implements android.view.View.OnClickListener{

	private ConfigEditor		configEditor;
	
	public ButtonListener(){}
	
	public ButtonListener(ConfigEditor configEditor){
		this.configEditor	=	configEditor;
	}

    public void onClick(View v) {
    	int id			=	v.getId();
    	/*int wifiSwitch	=	R.id.wifiSwitch;
    	int btSwitch	=	R.id.btSwitch;*/
    	int scanQr		=	R.id.scanQr;
    	int homeScanQr	=	R.id.homeScanQr;
    	/*if(id==blueOnId)
    		configEditor.bluetoothAldatu("On");
    	else if(id==blueOffId)
    		configEditor.bluetoothAldatu("Off");
    	else if(id==wifiOnId)
    		configEditor.wifiAldatu("On");
    	else if(id==wifiOffId)
    		configEditor.wifiAldatu("Off");*/
    	/*if(id==wifiSwitch){
    		Switch wSwitch	=	(Switch)v;
    		Boolean selection	=	wSwitch.hasSelection();
    		selection	=	false;
    	}
    	else if(id==btSwitch){
    		Switch bSwitch	=	(Switch)v;
    		Boolean selection	=	bSwitch.hasSelection();
    		selection	=	false;
    	}else */if(id==scanQr || id==homeScanQr)
    		configEditor.scanQrCode();
    }

}
