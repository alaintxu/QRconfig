package softwareaskea.qrconfig;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends android.app.Fragment {
	private	QRConfigActivity	qrca		=	null;
	private	ButtonListener	buttonListener	=	null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("Test","HomeFragment created");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
        qrca	=	(QRConfigActivity) this.getActivity();
        buttonListener	=	qrca.getButtonListener();

        Button homeScanQr = (Button) qrca.findViewById(R.id.homeScanQr);
        homeScanQr.setOnClickListener(buttonListener);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view	=	inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }
}
