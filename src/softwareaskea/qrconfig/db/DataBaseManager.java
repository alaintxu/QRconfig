package softwareaskea.qrconfig.db;

import android.content.Context;
import android.widget.Toast;
import softwareaskea.qrconfig.profiles.Profile;


public class DataBaseManager {
	private Context context;
	
	public DataBaseManager(){}
	
	public DataBaseManager(Context context){
		this.context	=	context;
	}

	public int saveConfigProfile(Profile profile){
		Toast.makeText(context, "not implemented yet", Toast.LENGTH_SHORT);
		return -1;
	}
	
	public Context getContext(){
		return context;
	}
	
	public void setContext(Context context){
		this.context	=	context;
	}

	public Profile getProfile(String profileName) {
		Toast.makeText(context, "not implemented yet", Toast.LENGTH_SHORT);
		return null;
	}
}
