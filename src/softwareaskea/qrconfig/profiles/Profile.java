package softwareaskea.qrconfig.profiles;

public class Profile {

	public static final int WIFI_ON				=	 1;
	public static final int BLUETOOTH_ON		=	 1;
	public static final int VIBRATION_ON		=	 1;
	
	public static final int WIFI_OFF			=	 0;
	public static final int BLUETOOTH_OFF		=	 0;
	public static final int VIBRATION_OFF		=	 0;
	
	public static final int WIFI_NOT_SET		=	-1;
	public static final int BLUETOOTH_NOT_SET	=	-1;
	public static final int VIBRATION_NOT_SET	=	-1;
	public static final int MULTIMEDIA_NOT_SET	=	-1;
	public static final int RINGTONE_NOT_SET	=	-1;
	public static final int ALARM_NOT_SET		=	-1;
	
	public static final String PROFILE_CODE		=	"P";
	public static final String WIFI_CODE		=	"W";
	public static final String BLUETOOTH_CODE	=	"BT";
	public static final String VIBRATION_CODE	=	"VB";
	public static final String RINGTONE_CODE	=	"RT";
	public static final String MULTIMEDIA_CODE	=	"M";
	public static final String ALARM_CODE		=	"AL";
	
	private long 	id;
	private String	name				=	null;
	private int		wifi				=	WIFI_NOT_SET;
	private int		bluetooth			=	BLUETOOTH_NOT_SET;
	private int		vibration			=	VIBRATION_NOT_SET;
	private int		ringtone			=	RINGTONE_NOT_SET;
	private int		multimedia			=	MULTIMEDIA_NOT_SET;
	private int		alarm				=	ALARM_NOT_SET;
	
	/************  Constructors  ************/
	public Profile(){}
	
	public Profile(
			String name,
			int wifi, 
			int bluetooth,
			int vibration,
			int multimedia,
			int ringtone,
			int alarm){
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setMultimedia(multimedia);
		this.setRingtone(ringtone);
		this.setAlarm(alarm);
	}
	
	public Profile(
			long id,
			String name,
			int wifi, 
			int bluetooth,
			int vibration,
			int multimedia,
			int ringtone,
			int alarm){
		this.setId(id);
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setMultimedia(multimedia);
		this.setRingtone(ringtone);
		this.setAlarm(alarm);
	}

	/************  getters & setters  ************/
	//id
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id	=	id;
	}
	
	//Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Wifi
	public int getWifi() {
		return wifi;
	}

	public void setWifi(int wifi) {
		this.wifi = wifi;
	}
	
	//Bluetooth
	public int getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(int bluetooth) {
		this.bluetooth = bluetooth;
	}

	//Vibration
	public int getVibration() {
		return vibration;
	}

	public void setVibration(int vibration) {
		this.vibration = vibration;
	}

	//Multimedia Volume
	public int getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(int multimedia) {
		this.multimedia = multimedia;
	}

	//Ringtone Volume
	public int getRingtone() {
		return ringtone;
	}

	public void setRingtone(int ringtone) {
		this.ringtone = ringtone;
	}
	
	//Alarm Volume
	public int getAlarm(){
		return alarm;
	}
	
	public void setAlarm(int alarm){
		this.alarm	=	alarm;
	}
}
