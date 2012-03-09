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
		
        // Capture our button from layout
        Button blueOn = (Button)qrca.findViewById(R.id.blueOn);
        Button blueOff = (Button)qrca.findViewById(R.id.blueOff);
        Button wifiOn = (Button)qrca.findViewById(R.id.wifiOn);
        Button wifiOff = (Button)qrca.findViewById(R.id.wifiOff);
        
        // Register the onClick listener with the implementation above
    	blueOn.setOnClickListener(buttonListener);
    	blueOff.setOnClickListener(buttonListener);
    	wifiOn.setOnClickListener(buttonListener);
    	wifiOff.setOnClickListener(buttonListener);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manual_fragment, container, false);
    }
}
