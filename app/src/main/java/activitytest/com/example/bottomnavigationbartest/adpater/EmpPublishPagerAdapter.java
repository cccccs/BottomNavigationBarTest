package activitytest.com.example.bottomnavigationbartest.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.PublishCheckFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.PublishDoneFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.PublishWantedFragment;

/**
 * Created by pc on 2017/8/27.
 */

public class EmpPublishPagerAdapter extends FragmentPagerAdapter {
    private String[] mTab = new String[]{"等待审核","正在招聘","截止报名"};

    public EmpPublishPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                return PublishCheckFragment.newInstance();
            case 1:
                return PublishWantedFragment.newInstance();
            case 2:
                return PublishDoneFragment.newInstance();
            default:
                return null;
        }
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
