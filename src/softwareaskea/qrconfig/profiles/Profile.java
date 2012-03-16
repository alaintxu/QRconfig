package softwareaskea.qrconfig.profiles;

public class Profile {

	public static final int BLUETOOTH_ON		=	 1;
	public static final int WIFI_ON				=	 1;
	public static final int VIBRATION_ON		=	 1;
	public static final int BLUETOOTH_OFF		=	 0;
	public static final int WIFI_OFF			=	 0;
	public static final int VIBRATION_OFF		=	 0;
	public static final int BLUETOOTH_NOT_SET	=	-1;
	public static final int WIFI_NOT_SET		=	-1;
	public static final int VIBRATION_NOT_SET	=	-1;
	
	public static final String WIFI_CODE		=	"W";
	public static final String BLUETOOTH_CODE	=	"BT";
	public static final String VIBRATION_CODE	=	"VB";
	public static final String VOLUME_CODE		=	"V";
	public static final String MULTIMEDIA_CODE	=	"VM";
	
	private long 	id;
	private String	name				=	null;
	private int		wifi				=	WIFI_NOT_SET;
	private int		bluetooth			=	BLUETOOTH_NOT_SET;
	private int		vibration			=	VIBRATION_NOT_SET;
	private int		volume				=	-1;		//-1 means not set or no change
	private int		multimediaVolume	=	-1;		//-1 means not set or no change
	
	/************  Constructors  ************/
	public Profile(){}
	
	public Profile(
			String name,
			int wifi, 
			int bluetooth, 
			int vibration,
			int volume,
			int multimediaVolume){
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setVolume(volume);
		this.setMultimediaVolume(multimediaVolume);
	}
	
	public Profile(
			long id,
			String name,
			int wifi, 
			int bluetooth, 
			int vibration,
			int volume,
			int multimediaVolume){
		this.setId(id);
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setVolume(volume);
		this.setMultimediaVolume(multimediaVolume);
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

	//Ringtone
	public int getVolume() {
		return volume;
	}

	public void setVolume(int ringtoneVolume) {
		this.volume = ringtoneVolume;
	}

	//Multimedia
	public int getMultimediaVolume() {
		return multimediaVolume;
	}

	public void setMultimediaVolume(int multimediaVolume) {
		this.multimediaVolume = multimediaVolume;
	}
	

	/************  For ListView  ************/
	@Override
	public String toString(){
		return name;
	}

}
