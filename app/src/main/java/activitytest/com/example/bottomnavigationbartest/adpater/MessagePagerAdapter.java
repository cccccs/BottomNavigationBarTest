package activitytest.com.example.bottomnavigationbartest.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activitytest.com.example.bottomnavigationbartest.ui.fragment.third.InformMesFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.third.LetterMesFragment;

/**
 * Created by pc on 2017/4/27.
 */

public class MessagePagerAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"系统通知","私信"};

    public MessagePagerAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position){
        if (position == 0)
            return InformMesFragment.instance();
        else
            return LetterMesFragment.instance();
    }

    @Override
    public int getCount(){
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTab[position];
    }
}
