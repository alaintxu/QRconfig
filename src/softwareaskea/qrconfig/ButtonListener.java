package softwareaskea.qrconfig;

import android.view.View;

public class ButtonListener implements android.view.View.OnClickListener{

	private QRConfigActivity	qrca;
	private ConfigEditor		configEditor;
	
	public ButtonListener(){}
	
	public ButtonListener(QRConfigActivity qrca){
		this.qrca			=	qrca;
		this.configEditor	=	qrca.getConfigEditor();
	}

    public void onClick(View v) {
    	int id			=	v.getId();
    	int blueOnId	=	R.id.blueOn;
    	int blueOffId	=	R.id.blueOff;
    	int wifiOnId	=	R.id.wifiOn;
    	int wifiOffId	=	R.id.wifiOff;
    	int scanQr		=	R.id.scanQr;
    	if(id==blueOnId)
    		configEditor.bluetoothAldatu("On");
    	else if(id==blueOffId)
    		configEditor.bluetoothAldatu("Off");
    	else if(id==wifiOnId)
    		configEditor.wifiAldatu("On");
    	else if(id==wifiOffId)
    		configEditor.wifiAldatu("Off");
    	else if(id==scanQr)
    		qrca.scanQrCode();
    }

}
