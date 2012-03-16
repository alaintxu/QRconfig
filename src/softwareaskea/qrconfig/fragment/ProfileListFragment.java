package softwareaskea.qrconfig.fragment;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.QRConfigActivity;
import softwareaskea.qrconfig.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileListFragment extends Fragment {
	private	QRConfigActivity	qrca		=	null;
	//private	ButtonListener	buttonListener	=	null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.e("Test","PresetsFragment created");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
        qrca							=	(QRConfigActivity) this.getActivity();
        ConfigEditor	configEditor	=	qrca.getConfigEditor();
        ListView 		listView 		= (ListView) qrca.findViewById(R.id.profileList);
        //buttonListener	=	qrca.getButtonListener();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		qrca,
        		android.R.layout.simple_list_item_1,
        		android.R.id.text1,
        		configEditor.getProfileListStr()
        );
        listView.setAdapter(adapter);
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_list_fragment, container, false);
    }
    

    @Override
    public void onResume(){
    	super.onResume();
    	qrca.updateViewStatus();
    	//updateProfileList();
    }
    
    /*public void updateProfileList(){
    	Context 		context			=	qrca.getApplicationContext();
    	ConfigEditor	configEditor	=	qrca.getConfigEditor();

    	try{
        	List<Profile>	profiles	=	configEditor.getProfileList();
        	ArrayAdapter<Profile>	adapter		=	new ArrayAdapter<Profile>(context, 0, profiles);
        	adapter	=	new ArrayAdapter<Profile>(this,android.R.layout.simple_list_item_1,R.id.profileList, profiles);
        	ListView		lv			=	(ListView) qrca.findViewById(R.id.profileList);
        	
    		lv.setAdapter(adapter);
    	}catch(Exception e){
    		Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    	}
    }*/
}
