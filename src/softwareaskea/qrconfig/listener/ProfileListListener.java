package softwareaskea.qrconfig.listener;

import java.util.ArrayList;

import softwareaskea.qrconfig.ConfigEditor;
import softwareaskea.qrconfig.QRFunctions;
import softwareaskea.qrconfig.R;
import softwareaskea.qrconfig.db.ProfileDAO;
import softwareaskea.qrconfig.profiles.Profile;
import softwareaskea.qrconfig.profiles.ProfileItemAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ProfileListListener implements OnItemClickListener {
	private Activity 			mActivity;
	private ConfigEditor		mConfigEditor;
	private ProfileDAO			mProfileDAO;
	private	ProfileItemAdapter	mAdapter;
	
	public ProfileListListener(){}
	public ProfileListListener(Activity activity,ProfileItemAdapter adapter){
		setActivity(activity);
		setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Profile profile	=	(Profile) adapter.getItemAtPosition(position);
		applyProfile(profile);
	}

	public void setActivity(Activity activity){
		mActivity		=	activity;
		mConfigEditor	=	new ConfigEditor(mActivity);
		mProfileDAO		=	new ProfileDAO(mActivity);
	}
	
	public void setAdapter(ProfileItemAdapter adapter){
		mAdapter	=	adapter;
	}
	
	public void applyProfile(Profile profile){
		final Profile 		mProfile	=	profile;
    	AlertDialog.Builder	alert		=	new AlertDialog.Builder(mActivity);
    	Resources			res			=	mActivity.getResources();
    	String 				title		=	res.getString(R.string.profile_apply_title)+": "+mProfile.getName();
    	String 				message		=	res.getString(R.string.profile_apply_message);
    	String				okBtn		=	res.getString(R.string.apply);
    	String				cancelBtn	=	res.getString(R.string.cancel);
    	
    	alert.setTitle(title);  
    	alert.setMessage(message);
    	
		//Ok button
    	alert.setPositiveButton(okBtn, 
    			new DialogInterface.OnClickListener(){  
    	    		public void onClick(DialogInterface dialog, int whichButton) {
    	    			mConfigEditor.applyProfile(mProfile);
    	    			Toast.makeText(mActivity, R.string.profile_applied, Toast.LENGTH_SHORT).show();
    	    			return;                  
    	    		}  
    	     	}
    	);  

    	alert.setNegativeButton(cancelBtn,
    			new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int which) {
    	        		return;   
    	        	}
    	    	}
    	);
    	alert.show();
	}
	
	public boolean onContextItemSelected(MenuItem item){
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	int position	=	info.position;
    	Profile profile	=	mAdapter.getItem(position);
    	Uri	uri			=	null;
        switch (item.getItemId()) {
        case R.id.context_menu_apply:
			mConfigEditor.applyProfile(profile);
			Toast.makeText(mActivity, R.string.profile_applied, Toast.LENGTH_SHORT).show();
            return true;
            case R.id.context_menu_edit:
            	//editProfile(info.id);
                return true;
            case R.id.context_menu_delete:
            	if(mProfileDAO.deleteProfile(profile)>0){
                	Toast.makeText(mActivity.getApplicationContext(), R.string.profile_deleted, Toast.LENGTH_LONG).show();
                	updateProfileList();
                	return true;
                }else{
                	Toast.makeText(mActivity.getApplicationContext(), R.string.error_profile_delete, Toast.LENGTH_LONG).show();
                	return false;
                }
            case R.id.context_menu_qr_config:
            	uri	=	QRFunctions.getQRURL(profile);
            	//Toast.makeText(mActivity.getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
            	openWebView(uri);
            	return true;
            case R.id.context_menu_qr_profile:
            	uri	=	QRFunctions.getQRURL(profile.getName());
            	//Toast.makeText(mActivity.getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
            	openWebView(uri);
            	return true;
            default:
                return false;
        }
    }
	
	private void openWebView(Uri uri){
		if(uri!=null){
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			mActivity.startActivity(intent);
		}else{
			Toast.makeText(mActivity.getApplicationContext(), R.string.error_qr_uri, Toast.LENGTH_SHORT).show();
		}
		
	}
	
    /******* Update *******/
    public void updateProfileList(){
    	mAdapter.clear();
    	mAdapter.addAll((ArrayList<Profile>) mProfileDAO.getAllProfiles());
    }
}
