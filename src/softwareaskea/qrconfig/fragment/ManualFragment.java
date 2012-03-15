package softwareaskea.qrconfig.fragment;

import softwareaskea.qrconfig.ConfigEditor;
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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class ManualFragment extends Fragment {
	private	QRConfigActivity	qrca;
	private	ButtonListener		buttonListener;
	private ConfigEditor		configEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("Test","ManualFragment created");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
        qrca			=	(QRConfigActivity) this.getActivity();
        buttonListener	=	qrca.getButtonListener();
        configEditor	=	qrca.getConfigEditor();
        
        Switch btSwitch			=	(Switch)	qrca.findViewById(R.id.btSwitch);
        Switch wifiSwitch		=	(Switch)	qrca.findViewById(R.id.wifiSwitch);
        Switch vbSwitch			=	(Switch)	qrca.findViewById(R.id.wifiSwitch);
        Button saveAsProfile	=	(Button)	qrca.findViewById(R.id.saveAsProfile);
        ImageButton volMaxBtn	=	(ImageButton)	qrca.findViewById(R.id.volMaxBtn);
        ImageButton volMuteBtn	=	(ImageButton)	qrca.findViewById(R.id.volMuteBtn);
        SeekBar rtSeekBar		=	(SeekBar)	qrca.findViewById(R.id.rtSeekBar);
        SeekBar nSeekBar		=	(SeekBar)	qrca.findViewById(R.id.nSeekBar);
        SeekBar mSeekBar		=	(SeekBar)	qrca.findViewById(R.id.mSeekBar);

        
        btSwitch.setOnClickListener(buttonListener);
        wifiSwitch.setOnClickListener(buttonListener);
        vbSwitch.setOnClickListener(buttonListener);
        saveAsProfile.setOnClickListener(buttonListener);
        volMaxBtn.setOnClickListener(buttonListener);
        volMuteBtn.setOnClickListener(buttonListener);
        
        rtSeekBar.setMax(configEditor.getRTMaxVolume());
        nSeekBar.setMax(configEditor.getNMaxVolume());
        mSeekBar.setMax(configEditor.getMMaxVolume());
        
        rtSeekBar.setOnSeekBarChangeListener(buttonListener);
        nSeekBar.setOnSeekBarChangeListener(buttonListener);
        mSeekBar.setOnSeekBarChangeListener(buttonListener);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.manual_fragment, container, false);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	qrca.updateViewStatus();
    }
}
