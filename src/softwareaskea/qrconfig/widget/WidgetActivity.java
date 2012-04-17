package softwareaskea.qrconfig.widget;

import softwareaskea.qrconfig.ConfigEditor;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WidgetActivity extends Activity {
	
	private ConfigEditor configEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configEditor	=	new ConfigEditor(this);
	}
	
	@Override
	public void onStart(){
        //configEditor.scanQrCode();
	}
    
    /**
     * Manage result of QR code scan
     * @param requestCode	0 if there is any result
     * @param resultCode	the type of result
     * @param intent	Barcode Scanner Intent
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                int x;
				if(format.equals("QR_CODE"))
                	x=5;
                	//configEditor.processQr(contents);
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
