package softwareaskea.qrconfig.fragment;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.listener.ManualListener;
import android.support.v4.app.Fragment;
import android.app.Activity;
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
	private	ManualListener		listener;
	//private ConfigEditor		mConfigEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("Test","ManualFragment created");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		Activity		activity		=	this.getActivity();
		ConfigEditor	mConfigEditor	=	new ConfigEditor(activity);
        listener						=	new ManualListener(this.getActivity());
        
        //Switches
        Switch btSwitch			=	(Switch)		activity.findViewById(R.id.btSwitch);
        Switch wifiSwitch		=	(Switch)		activity.findViewById(R.id.wifiSwitch);
        Switch vbSwitch			=	(Switch)		activity.findViewById(R.id.vbSwitch);
        
        btSwitch.setOnClickListener(listener);
        wifiSwitch.setOnClickListener(listener);
        vbSwitch.setOnClickListener(listener);
        
        //SeekBars
        SeekBar mSeekBar		=	(SeekBar)		activity.findViewById(R.id.mSeekBar);
        SeekBar rtSeekBar		=	(SeekBar)		activity.findViewById(R.id.rtSeekBar);
        SeekBar alSeekBar		=	(SeekBar)		activity.findViewById(R.id.alSeekBar);

        mSeekBar.setMax(mConfigEditor.getMMaxVolume());
        rtSeekBar.setMax(mConfigEditor.getRTMaxVolume());
        alSeekBar.setMax(mConfigEditor.getAlMaxVolume());

        mSeekBar.setOnSeekBarChangeListener(listener);
        rtSeekBar.setOnSeekBarChangeListener(listener);
        alSeekBar.setOnSeekBarChangeListener(listener);
        
        //Buttons
        Button saveAsProfile	=	(Button)		activity.findViewById(R.id.saveAsProfile);
        ImageButton volMaxBtn	=	(ImageButton)	activity.findViewById(R.id.volMaxBtn);
        ImageButton volMuteBtn	=	(ImageButton)	activity.findViewById(R.id.volMuteBtn);

        saveAsProfile.setOnClickListener(listener);
        volMaxBtn.setOnClickListener(listener);
        volMuteBtn.setOnClickListener(listener);
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
    	listener.updateManualViewStatus();
    }
}
