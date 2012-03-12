package softwareaskea.qrconfig.fragment;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class QRConfigPagerAdapter extends FragmentPagerAdapter {
	
	//private Resources	res;
	private final List<Fragment> fragments;
	private final String[] fragmentTitles;
	
	public QRConfigPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragment,String[] fragmentTitles){
		super(fragmentManager);
		this.fragments		=	fragment;
		this.fragmentTitles	=	fragmentTitles;
	}

	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}
	
	@Override
	public CharSequence getPageTitle (int position){
		return fragmentTitles[position];
	}
}
