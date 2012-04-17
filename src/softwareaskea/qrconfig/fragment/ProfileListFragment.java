package softwareaskea.qrconfig.fragment;

import java.util.ArrayList;

import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.db.ProfileDAO;
import softwareaskea.qrconfig.listener.ProfileListListener;
import softwareaskea.qrconfig.profiles.Profile;
import softwareaskea.qrconfig.profiles.ProfileItemAdapter;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProfileListFragment extends Fragment {
	private ProfileItemAdapter	mAdapter		=	null;
	private ProfileDAO			profileDAO		=	null;
	private ProfileListListener	listener;

    
    /******* Overrides *******/
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Activity	activity	=	this.getActivity();
		
		profileDAO						=	new ProfileDAO(activity);
		
        ListView 		listView 		=	(ListView) activity.findViewById(R.id.profile_list);
        ArrayList<Profile>	profiles	=	(ArrayList<Profile>) profileDAO.getAllProfiles();
        mAdapter							=	new ProfileItemAdapter(
        		this.getActivity().getApplicationContext(),
        		R.layout.profile_list_item,
        		profiles
        );
		listener	=	new ProfileListListener(activity,mAdapter);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(listener);
        registerForContextMenu(listView);
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
    	listener.updateProfileList();
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	profileDAO.close();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getActivity().getMenuInflater();
        if(v.getId()==R.id.profile_list){
        	inflater.inflate(R.menu.context_menu, menu);
        }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item){
    	return listener.onContextItemSelected(item);
    }
}
