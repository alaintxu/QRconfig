package softwareaskea.qrconfig.profiles;

public class Profile {
	private long 	id;
	private String	name				=	null;
	private Boolean	wifi				=	null;	//null means not set or no change
	private Boolean bluetooth			=	null;	//null means not set or no change
	private Boolean vibration			=	null;	//null means not set or no change
	private int		ringtoneVolume		=	-1;		//-1 means not set or no change
	private int		notificationVolume	=	-1;		//-1 means not set or no change
	private int		multimediaVolume	=	-1;		//-1 means not set or no change
	
	/************  Constructors  ************/
	public Profile(){}
	
	public Profile(
			String name,
			Boolean wifi, 
			Boolean bluetooth, 
			Boolean vibration,
			int toneVolume,
			int notificationVolume,
			int multimediaVolume){
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setRingtoneVolume(toneVolume);
		this.setNotificationVolume(notificationVolume);
		this.setMultimediaVolume(multimediaVolume);
	}
	
	public Profile(
			long id,
			String name,
			Boolean wifi, 
			Boolean bluetooth, 
			Boolean vibration,
			int toneVolume,
			int notificationVolume,
			int multimediaVolume){
		this.setId(id);
		this.setName(name);
		this.setWifi(wifi);
		this.setBluetooth(bluetooth);
		this.setVibration(vibration);
		this.setRingtoneVolume(toneVolume);
		this.setNotificationVolume(notificationVolume);
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
	public Boolean getWifi() {
		return wifi;
	}

	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}
	
	public void setWifi(int wifi) {
		if(wifi==1)
			this.wifi	=	true;
		else if(wifi==0)
			this.wifi	=	false;
		else
			this.wifi	=	null;
	}
	
	//Bluetooth
	public Boolean getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(Boolean bluetooth) {
		this.bluetooth = bluetooth;
	}
	
	public void setBluetooth(int bluetooth) {
		if(bluetooth==1)
			this.bluetooth	=	true;
		else if(bluetooth==0)
			this.bluetooth	=	false;
		else
			this.bluetooth	=	null;
	}

	//Vibration
	public Boolean getVibration() {
		return vibration;
	}

	public void setVibration(Boolean vibration) {
		this.vibration = vibration;
	}
	
	public void setVibration(int vibration) {
		if(vibration==1)
			this.vibration	=	true;
		else if(vibration==0)
			this.vibration	=	false;
		else
			this.vibration	=	null;
	}

	//Ringtone
	public int getRingtoneVolume() {
		return ringtoneVolume;
	}

	public void setRingtoneVolume(int ringtoneVolume) {
		this.ringtoneVolume = ringtoneVolume;
	}

	//Notification
	public int getNotificationVolume() {
		return notificationVolume;
	}

	public void setNotificationVolume(int notificationVolume) {
		this.notificationVolume = notificationVolume;
	}

	//Multimedia
	public int getMultimediaVolume() {
		return multimediaVolume;
	}

	public void setMultimediaVolume(int multimediaVolume) {
		this.multimediaVolume = multimediaVolume;
	}
	

	/************  For ListView  ************/
	public String toString(){
		return name;
	}

}
