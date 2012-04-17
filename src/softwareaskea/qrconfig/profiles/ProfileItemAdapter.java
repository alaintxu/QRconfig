package softwareaskea.qrconfig.profiles;

import java.util.ArrayList;

import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.profiles.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileItemAdapter extends ArrayAdapter<Profile> {
    private ArrayList<Profile> profiles;

    public ProfileItemAdapter(Context context, int textViewResourceId, ArrayList<Profile> arrayList) {
        super(context, textViewResourceId, arrayList);
        this.profiles = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = convertView;
       if (v == null) {
    	   LayoutInflater vi	=	(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           v = vi.inflate(R.layout.profile_list_item, null);
        }
        Profile profile = profiles.get(position);
        if (profile != null) {
        	TextView name	= (TextView) v.findViewById(R.id.pli_name);
        	
        	ImageView bt	= (ImageView) v.findViewById(R.id.pli_bt);
        	ImageView wifi	= (ImageView) v.findViewById(R.id.pli_wifi);
        	ImageView vb	= (ImageView) v.findViewById(R.id.pli_vb);
        	ImageView rtVol	= (ImageView) v.findViewById(R.id.pli_rt_vol);
        	ImageView alVol	= (ImageView) v.findViewById(R.id.pli_al_vol);
        	ImageView mVol	= (ImageView) v.findViewById(R.id.pli_m_vol);
        	
        	int value;
        	int imageSrc;
        	
		    if (name != null) {
		    	name.setText(profile.getName());
		    }
		
		    if(bt != null) {
		    	value	=	profile.getBluetooth();
		    	if(value != Profile.BLUETOOTH_NOT_SET){
		    		bt.setVisibility(ImageView.VISIBLE);
		    		if(value==Profile.BLUETOOTH_ON)
		    			imageSrc=R.drawable.ic_audio_bt;
		    		else
		    			imageSrc=R.drawable.ic_audio_bt_mute;
		    		bt.setBackgroundResource(imageSrc);
		    	}else
		    		bt.setVisibility(ImageView.GONE);
		    }
		
		    if(wifi != null) {
		    	value	=	profile.getWifi();
		    	if(value != Profile.WIFI_NOT_SET){
		    		wifi.setVisibility(ImageView.VISIBLE);
		    		if(value==Profile.WIFI_ON)
		    			imageSrc=R.drawable.wifi;
		    		else
		    			imageSrc=R.drawable.wifi_off;
		    		wifi.setBackgroundResource(imageSrc);
		    	}else
		    		wifi.setVisibility(ImageView.GONE);
		    }
		
		    if(vb != null) {
		    	value	=	profile.getVibration();
	    		if(value==Profile.VIBRATION_ON){
		    		vb.setVisibility(ImageView.VISIBLE);
		    		imageSrc=R.drawable.ic_audio_ring_notif_vibrate;
		    		vb.setBackgroundResource(imageSrc);
		    	}else
		    		vb.setVisibility(ImageView.GONE);
		    }
		
		    if(mVol != null) {
		    	value	=	profile.getMultimedia();
		    	if(value != Profile.MULTIMEDIA_NOT_SET){
		    		mVol.setVisibility(ImageView.VISIBLE);
		    		if(value!=0)
		    			imageSrc=R.drawable.ic_volume;
		    		else
		    			imageSrc=R.drawable.ic_volume_off;
		    	}else
		    		mVol.setVisibility(ImageView.GONE);
		    }
		
		    if(rtVol != null) {
		    	value	=	profile.getRingtone();
		    	if(value != Profile.RINGTONE_NOT_SET){
		    		rtVol.setVisibility(ImageView.VISIBLE);
		    		if(value>0)
		    			imageSrc=R.drawable.ic_audio_ring_notif;
		    		else
		    			imageSrc=R.drawable.ic_audio_ring_notif_mute;
		    		rtVol.setBackgroundResource(imageSrc);
		    	}else
		    		rtVol.setVisibility(ImageView.GONE);
		    }
		
		    if(alVol != null) {
		    	value	=	profile.getVibration();
		    	if(value != Profile.ALARM_NOT_SET){
		    		alVol.setVisibility(ImageView.VISIBLE);
		    		if(value>0)
		    			imageSrc=R.drawable.ic_audio_alarm;
		    		else
		    			imageSrc=R.drawable.ic_audio_alarm_mute;
		    		alVol.setBackgroundResource(imageSrc);
		    	}else
		    		alVol.setVisibility(ImageView.GONE);
		    }
		}
		return v;
    }
}

