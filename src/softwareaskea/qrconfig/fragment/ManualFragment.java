package softwareaskea.qrconfig.fragment;

import softwareaskea.qrconfig.QRConfigActivity;
import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.listener.ButtonListener;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

public class ManualFragment extends Fragment {
	private	QRConfigActivity	qrca		=	null;
	private	ButtonListener	buttonListener	=	null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("Test","ManualFragment created");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
        qrca	=	(QRConfigActivity) this.getActivity();
        buttonListener	=	qrca.getButtonListener();
		
        /*Switch btSwitch		=	(Switch)qrca.findViewById(R.id.btSwitch);
        Switch wifiSwitch	=	(Switch)qrca.findViewById(R.id.wifiSwitch);
        
        btSwitch.setOnClickListener(buttonListener);
        wifiSwitch.setOnClickListener(buttonListener);*/
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manual_fragment, container, false);
    }
}
