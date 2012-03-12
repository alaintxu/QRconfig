package softwareaskea.qrconfig.db;

import android.content.Context;
import android.widget.Toast;
import softwareaskea.qrconfig.ConfigProfile;


public class DataBaseManager {
	private Context context;
	
	public DataBaseManager(){}
	
	public DataBaseManager(Context context){
		this.context	=	context;
	}

	public int saveConfigProfile(ConfigProfile profile){
		Toast.makeText(context, "not implemented yet", Toast.LENGTH_SHORT);
		return -1;
	}
	
	public Context getContext(){
		return context;
	}
	
	public void setContext(Context context){
		this.context	=	context;
	}

	public ConfigProfile getProfile(String profileName) {
		Toast.makeText(context, "not implemented yet", Toast.LENGTH_SHORT);
		return null;
	}
}
