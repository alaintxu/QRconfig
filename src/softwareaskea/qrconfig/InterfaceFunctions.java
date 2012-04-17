package softwareaskea.qrconfig;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;

public class InterfaceFunctions {

	/**
	 * Get Switch value
	 * @param checkBoxId	id of CheckBox resource
	 * @param switchId		id of the Switch resource
	 * @param defaultValue	default value if CheckBox is not enabled
	 * @param onValue		value to return if Switch is checked
	 * @param offValue		value to return if Switch is not checked
	 * @return Switch
	 */
	public static int getSwitchValue(Activity activity, int checkBoxId,int switchId,int defaultValue, int offValue, int onValue){
		int 		value		=	defaultValue;
		CheckBox	checkBox	=	(CheckBox)activity.findViewById(checkBoxId);
		if(checkBox.isChecked()){
			Switch mSwitch	=	(Switch)activity.findViewById(switchId);
			if(mSwitch.isChecked()) value	=	onValue;
			else					value	=	offValue;
		}
		
		return value;
	}
	
	/**
	 * Get SeekBar Progress value
	 * @param checkBoxId	id of CheckBox resource
	 * @param seekBarId		id of the SeekBar resource
	 * @param defaultValue	default value if checkbox is not enabled
	 * @return SeekBar-s progress value
	 */
	public static int getSeekBarValue(Activity activity, int checkBoxId,int seekBarId,int defaultValue){
		int 		value		=	defaultValue;
		CheckBox	checkBox	=	(CheckBox)activity.findViewById(checkBoxId);
		if(checkBox.isChecked()){
			SeekBar mSeekBar	=	(SeekBar)activity.findViewById(seekBarId);
			value				=	mSeekBar.getProgress();
		}
		
		return value;
	}
}
