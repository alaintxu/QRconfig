package softwareaskea.qrconfig;

public class ConfigProfile {
	
	private String	name				=	null;
	private Boolean	wifi				=	null;	//null means not set or no change
	private Boolean bluetooth			=	null;	//null means not set or no change
	private Boolean vibration			=	null;	//null means not set or no change
	private int		toneVolume			=	-1;		//-1 means not set or no change
	private int		notificationVolume	=	-1;		//-1 means not set or no change
	private int		multimediaVolume	=	-1;		//-1 means not set or no change
	
	/************  Constructors  ************/
	public ConfigProfile(){}
	
	public ConfigProfile(
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
		this.setToneVolume(toneVolume);
		this.setNotificationVolume(notificationVolume);
		this.setMultimediaVolume(multimediaVolume);
	}

	/************  getters & setters  ************/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWifi() {
		return wifi;
	}

	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}

	public Boolean getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(Boolean bluetooth) {
		this.bluetooth = bluetooth;
	}

	public Boolean getVibration() {
		return vibration;
	}

	public void setVibration(Boolean vibration) {
		this.vibration = vibration;
	}

	public int getToneVolume() {
		return toneVolume;
	}

	public void setToneVolume(int toneVolume) {
		this.toneVolume = toneVolume;
	}

	public int getNotificationVolume() {
		return notificationVolume;
	}

	public void setNotificationVolume(int notificationVolume) {
		this.notificationVolume = notificationVolume;
	}

	public int getMultimediaVolume() {
		return multimediaVolume;
	}

	public void setMultimediaVolume(int multimediaVolume) {
		this.multimediaVolume = multimediaVolume;
	}

}
