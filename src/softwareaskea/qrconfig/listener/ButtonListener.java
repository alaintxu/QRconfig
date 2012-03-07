package softwareaskea.qrconfig.listener;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.QRConfigActivity;
import softwareaskea.qrconfig.R;
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
    	//int homeScanQr	=	R.id.homeScanQr;
    	if(id==blueOnId)
    		configEditor.bluetoothAldatu("On");
    	else if(id==blueOffId)
    		configEditor.bluetoothAldatu("Off");
    	else if(id==wifiOnId)
    		configEditor.wifiAldatu("On");
    	else if(id==wifiOffId)
    		configEditor.wifiAldatu("Off");
    	else if(id==scanQr/* || id==homeScanQr*/)
    		qrca.scanQrCode();
    }

}
