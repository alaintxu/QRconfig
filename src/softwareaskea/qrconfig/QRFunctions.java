package softwareaskea.qrconfig;

import android.net.Uri;

import softwareaskea.qrconfig.db.ProfileDAO;
import softwareaskea.qrconfig.profiles.Profile;

public class QRFunctions {
	public static Profile getQRProfile(String qr,ProfileDAO mProfileDAO){
		Profile	profile	=	new Profile();
    	String 	pattern	=	"MCONFIG:(\\S+:\\S+;)*;";
    	if(qr.matches(pattern)){
    		qr = qr.replace("MCONFIG:","");
    		qr = qr.replace(";;","");
    		String[] split = qr.split(";");
    		for (String s : split) {
    		      String[] subsplit = s.split(":");
    		      if(subsplit[0].equals("P"))
    		    	  try{
    		    		  return mProfileDAO.loadProfile(subsplit[1]);
    		    	  }catch(Exception e){
    		    		  return null;
    		    	  }
    		      else if(subsplit[0].equals(Profile.WIFI_CODE))
    		    	  profile.setWifi(Integer.parseInt(subsplit[1]));
    		      else if(subsplit[0].equals(Profile.BLUETOOTH_CODE))
    		    	  profile.setBluetooth(Integer.parseInt(subsplit[1]));
    		      else if(subsplit[0].equals(Profile.VIBRATION_CODE))
    		    	  profile.setVibration(Integer.parseInt(subsplit[1]));
    		      else if(subsplit[0].equals(Profile.RINGTONE_CODE))
    		    	  profile.setRingtone(Integer.parseInt(subsplit[1]));
    		      else if(subsplit[0].equals(Profile.MULTIMEDIA_CODE))
    		    	  profile.setMultimedia(Integer.parseInt(subsplit[1]));
    		      else if(subsplit[0].equals(Profile.ALARM_CODE))
    		    	  profile.setVibration(Integer.parseInt(subsplit[1]));
    		}
    		return profile;
    	}
    	return null;
    }
	
	public static Uri getQRURL(String name){
		String	data		=	"MCONFIG:P:"+name+";;";
		String	uriString	=	getGoogleChartQRLink(data);
		return Uri.parse(uriString);
	}
	
	public static Uri getQRURL(Profile profile){
		String	data	=	getQRString(profile);
		Uri		uri		=	null;
		if(!data.equals(""))
			uri	=	Uri.parse(getGoogleChartQRLink(data));
		
		return uri;
	}
	
	private static String getQRString(Profile profile){
		String data	=	"MCONFIG:";
		//WiFi
		int wifi	=	profile.getWifi();
		if(wifi!=Profile.WIFI_NOT_SET)
			data	+=	Profile.WIFI_CODE+":"+wifi+";";
		//Bluetooth
		int bluetooth	=	profile.getBluetooth();
		if(wifi!=Profile.BLUETOOTH_NOT_SET)
			data	+=	Profile.BLUETOOTH_CODE+":"+bluetooth+";";
		//Vibration
		int vibration	=	profile.getVibration();
		if(vibration!=Profile.VIBRATION_NOT_SET)
			data	+=	Profile.VIBRATION_CODE+":"+vibration+";";
		//RingTone
		int ringtone	=	profile.getRingtone();
		if(ringtone!=Profile.RINGTONE_NOT_SET)
			data	+=	Profile.RINGTONE_CODE+":"+ringtone+";";
		//Multimedia
		int multimedia	=	profile.getMultimedia();
		if(multimedia!=Profile.MULTIMEDIA_NOT_SET)
			data	+=	Profile.MULTIMEDIA_CODE+":"+multimedia+";";
		//Alarm
		int alarm	=	profile.getAlarm();
		if(alarm!=Profile.ALARM_NOT_SET)
			data	+=	Profile.ALARM_CODE+":"+alarm+";";
		data	+=	";";
		if(!data.equals("MCONFIG:;"))
			return data;
		else
			return "";
	}
	
    private static String getGoogleChartQRLink(String data){
    	String	link	=	getGoogleChartQRLink(256,256,data);
    	return link;
    }
    
	private static String getGoogleChartQRLink(int width, int height, String data){
    	String	link	=	"https://chart.googleapis.com/chart?chs="+width+"x"+height+"&cht=qr&chl="+data;
    	return link;
    }
}
